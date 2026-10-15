package com.jiltsa.admin.seller.domain.service;

import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.mapper.SellerMapper;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository repository;
    private final SellerMapper mapper;

    public List<SellerDto> getAllSellers() {
        return mapper.toSellerDtoList(repository.findByIsActiveTrue());
    }

    public List<SellerDto> getSellersByBranch(Integer branchId) {
        return mapper.toSellerDtoList(repository.findByBranchIdAndIsActiveTrue(branchId));
    }

    @Transactional
    public SellerDto createSeller(SellerDto sellerDto) {
        Seller seller = mapper.toSeller(sellerDto);
        return mapper.toSellerDto(repository.save(seller));
    }

    @Transactional
    public SellerDto disableSeller(Integer sellerId) {
        return mapper.toSellerDto(repository.findById(sellerId).map(seller -> {
            seller.setIsActive(false);
            return repository.save(seller);
        }).orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId)));
    }

    public Optional<SellerDto> getSeller(Integer sellerId) {
        return repository.findById(sellerId).map(mapper::toSellerDto);
    }
}
