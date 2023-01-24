package com.jiltsa.admin.seller.persistence.mapper;

import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    SellerDto toSellerDto(Seller seller);
    List<SellerDto> toSellersDto(List<Seller> sellers);

    @InheritInverseConfiguration
    @Mapping(target = "branch", ignore = true)
    Seller toSeller(SellerDto sellerDto);
}
