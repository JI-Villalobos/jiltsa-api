package com.jiltsa.admin.seller.persistence.mapper;

import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    SellerDto toSellerDto(Seller seller);
    List<SellerDto> toSellerDtoList(List<Seller> sellers);

    @InheritInverseConfiguration
    @Mapping(target = "branch", ignore = true)
    Seller toSeller(SellerDto sellerDto);

    /** Name, branch, active flag and turn; the id and the association are left alone. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    void updateFromDto(SellerDto sellerDto, @MappingTarget Seller seller);
}
