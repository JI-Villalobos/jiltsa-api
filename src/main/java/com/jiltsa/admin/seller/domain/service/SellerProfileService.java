package com.jiltsa.admin.seller.domain.service;

import com.jiltsa.admin.common.exception.ConflictException;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.seller.domain.dto.SellerProfileDto;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.entity.SellerProfile;
import com.jiltsa.admin.seller.persistence.mapper.SellerProfileMapper;
import com.jiltsa.admin.seller.persistence.repository.SellerProfileRepository;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import com.jiltsa.admin.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * One profile per seller, created once and then updated. The seller fills her own form in
 * (any account of her branch may send it); an admin may send it for employees who already
 * answered the previous form. Profiles are never deleted: a seller who leaves is disabled, and
 * the record (and its documents) stays as the employee's file.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SellerProfileService {
    private final SellerProfileRepository repository;
    private final SellerRepository sellerRepository;
    private final SellerProfileMapper mapper;
    /** Same roles AdminOnly accepts. */
    private static final Set<String> ADMIN_AUTHORITIES = Set.of("ADMIN", "DEVELOPER");

    public Optional<SellerProfileDto> getProfile(Integer profileId) {
        return repository.findById(profileId).map(mapper::toSellerProfileDto);
    }

    public Optional<SellerProfileDto> getProfileBySeller(Integer sellerId) {
        return repository.findBySellerId(sellerId).map(mapper::toSellerProfileDto);
    }

    public List<SellerProfileDto> getProfilesByBranch(Integer branchId) {
        return mapper.toSellerProfileDtoList(repository.findBySellerBranchIdOrderByFullNameAsc(branchId));
    }

    /** {@code registrar} is the authenticated account; a non-admin may only register sellers of its branch. */
    @Transactional
    public SellerProfileDto createProfile(SellerProfileDto profileDto, Authentication registrar) {
        Seller seller = sellerRepository.findById(profileDto.sellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller", profileDto.sellerId()));
        requireSameBranchUnlessAdmin(registrar, seller);
        repository.findBySellerId(profileDto.sellerId()).ifPresent(existing -> {
            throw new ConflictException("Seller " + profileDto.sellerId() + " already has profile " + existing.getId());
        });
        SellerProfile profile = new SellerProfile();
        mapper.updateFromDto(normalized(profileDto), profile);
        profile.setSellerId(seller.getId());
        profile.setSeller(seller); // read-only association, set so the response can carry the branch
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(profile.getCreatedAt());
        profile.setPrivacyNoticeAcceptedAt(profile.getCreatedAt());
        profile.setRegisteredBy(registrar.getName());
        return mapper.toSellerProfileDto(repository.save(profile));
    }

    /** Every form field may change; the seller the profile belongs to may not. */
    @Transactional
    public SellerProfileDto updateProfile(Integer profileId, SellerProfileDto profileDto) {
        SellerProfile profile = repository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerProfile", profileId));
        if (!profile.getSellerId().equals(profileDto.sellerId())) {
            throw new ConflictException("Profile " + profileId + " belongs to seller " + profile.getSellerId()
                    + "; it cannot be moved to seller " + profileDto.sellerId());
        }
        mapper.updateFromDto(normalized(profileDto), profile);
        profile.setUpdatedAt(LocalDateTime.now());
        return mapper.toSellerProfileDto(repository.saveAndFlush(profile));
    }

    private static void requireSameBranchUnlessAdmin(Authentication registrar, Seller seller) {
        boolean admin = registrar.getAuthorities().stream()
                .anyMatch(authority -> ADMIN_AUTHORITIES.contains(authority.getAuthority()));
        if (admin) return;
        if (!(registrar.getPrincipal() instanceof AppUser user) || !seller.getBranchId().equals(user.getBranchId())) {
            throw new AccessDeniedException("Seller " + seller.getId() + " belongs to another branch; only an admin can register it");
        }
    }

    private static SellerProfileDto normalized(SellerProfileDto dto) {
        return new SellerProfileDto(dto.id(), dto.sellerId(), dto.fullName().trim(), dto.birthDate(), dto.phone().trim(),
                upper(dto.rfc()), upper(dto.curp()), dto.emergencyContactName().trim(), dto.emergencyContactPhone().trim(),
                dto.hireDate(), dto.position().trim(), dto.salary(), dto.privacyNoticeAccepted(),
                dto.branchId(), dto.privacyNoticeAcceptedAt(), dto.registeredBy(), dto.createdAt(), dto.updatedAt());
    }

    private static String upper(String value) {
        if (value == null || value.isBlank()) return null;
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
