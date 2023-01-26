package com.jiltsa.admin.seller.persistence.repository;

import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.domain.repository.SellerDRepository;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryImplementation implements SellerDRepository {
    private final SellerRepository repository;
    private final SellerMapper mapper;
    @Override
    public List<SellerDto> getAllSellers() {
        return mapper.toSellersDto(repository.findByIsActiveTrue());
    }

    @Override
    public Optional<SellerDto> getSeller(Integer sellerId) {
        return repository.findById(sellerId).map(mapper::toSellerDto);
    }

    @Override
    public SellerDto newSeller(SellerDto sellerDto) {
        Seller seller = mapper.toSeller(sellerDto);
        return mapper.toSellerDto(repository.save(seller));
    }

    @Override
    public SellerDto disableSeller(Integer sellerId) {
        return mapper.toSellerDto(repository.findById(sellerId).map(seller -> {
            seller.setIsActive(false);
            return repository.save(seller);
        }).orElseThrow());
    }
}
