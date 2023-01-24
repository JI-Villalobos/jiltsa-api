package com.jiltsa.admin.seller.domain.repository;

import com.jiltsa.admin.seller.domain.dto.SellerDto;

import java.util.List;
import java.util.Optional;

public interface SellerDRepository {
    List<SellerDto> getAllSellers();
    Optional<SellerDto> getSeller(Integer sellerId);
    SellerDto newSeller(SellerDto sellerDto);
    SellerDto disableSeller(Integer idSeller);
}
