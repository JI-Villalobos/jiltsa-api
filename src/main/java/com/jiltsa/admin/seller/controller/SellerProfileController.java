package com.jiltsa.admin.seller.controller;

import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import com.jiltsa.admin.seller.domain.dto.SellerProfileDto;
import com.jiltsa.admin.seller.domain.service.SellerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Sending the form is open to every account (the seller fills her own in, for a seller of her
 * branch), and so is reading one seller's profile, so the seller can check her own and ask an
 * admin for corrections. Listing profiles and editing them, which carry salary and identity
 * data, is ADMIN only.
 */
@RestController
@RequestMapping("jiltsa/api/v1/seller-profiles")
@RequiredArgsConstructor
public class SellerProfileController {
    private final SellerProfileService service;

    @Operation(summary = "Get a seller profile")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No profile with that id")
    @AdminOnly
    @GetMapping("/{profileId}")
    public SellerProfileDto getProfile(@Parameter(description = "Profile id") @PathVariable("profileId") Integer profileId) {
        return service.getProfile(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerProfile", profileId));
    }

    @Operation(summary = "Get the profile of a seller",
            description = "Open to every account: the seller checks her own profile and asks an admin for any correction.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "The seller has no profile")
    @GetMapping("/by-seller/{sellerId}")
    public SellerProfileDto getProfileBySeller(@Parameter(description = "Seller id") @PathVariable("sellerId") Integer sellerId) {
        return service.getProfileBySeller(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerProfile for seller", sellerId));
    }

    @Operation(summary = "List the profiles of a branch's sellers", description = "Ordered by full name; includes disabled sellers.")
    @AdminOnly
    @GetMapping("/by-branch/{branchId}")
    public List<SellerProfileDto> getProfilesByBranch(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId) {
        return service.getProfilesByBranch(branchId);
    }

    @Operation(summary = "Send the employee form of a seller",
            description = "One profile per seller. The seller sends her own form after accepting the privacy notice "
                    + "(privacyNoticeAccepted must be true); any account may do it for a seller of its branch, and an "
                    + "admin for any seller, which is how employees who answered the previous form are registered. "
                    + "Upload the INE pictures and the address proof afterwards through /files/upload-url "
                    + "(ownerType SELLER_PROFILE, documentType INE_FRONT, INE_BACK or ADDRESS_PROOF).")
    @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "403", description = "The seller belongs to another branch and the account is not an admin")
    @ApiResponse(responseCode = "404", description = "No seller with that id")
    @ApiResponse(responseCode = "409", description = "The seller already has a profile")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerProfileDto createProfile(@Valid @RequestBody SellerProfileDto profileDto, Authentication registrar) {
        return service.createProfile(profileDto, registrar);
    }

    @Operation(summary = "Update a seller profile", description = "Every form field; the seller it belongs to cannot change.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No profile with that id")
    @ApiResponse(responseCode = "409", description = "sellerId differs from the profile's seller")
    @AdminOnly
    @PutMapping("/{profileId}")
    public SellerProfileDto updateProfile(
            @Parameter(description = "Profile id") @PathVariable("profileId") Integer profileId,
            @Valid @RequestBody SellerProfileDto profileDto) {
        return service.updateProfile(profileId, profileDto);
    }
}
