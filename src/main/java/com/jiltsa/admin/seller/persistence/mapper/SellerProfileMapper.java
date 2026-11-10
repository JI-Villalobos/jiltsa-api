package com.jiltsa.admin.seller.persistence.mapper;

import com.jiltsa.admin.seller.domain.dto.SellerProfileDto;
import com.jiltsa.admin.seller.persistence.entity.SellerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerProfileMapper {
    @Mapping(target = "branchId", source = "seller.branchId")
    SellerProfileDto toSellerProfileDto(SellerProfile profile);
    List<SellerProfileDto> toSellerProfileDtoList(List<SellerProfile> profiles);

    /** The form fields only: id, seller, timestamps and the association are managed by the service. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sellerId", ignore = true)
    @Mapping(target = "privacyNoticeAcceptedAt", ignore = true)
    @Mapping(target = "registeredBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "seller", ignore = true)
    void updateFromDto(SellerProfileDto dto, @MappingTarget SellerProfile profile);
}
