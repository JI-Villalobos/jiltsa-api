package com.jiltsa.admin.promotions.persistence.mapper;

import com.jiltsa.admin.promotions.domain.dto.PromotionDto;
import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PromotionItemMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PromotionMapper {
    PromotionDto toPromotionDto(Promotion promotion);
    List<PromotionDto> toPromotionDtoList(List<Promotion> promotions);

    @InheritInverseConfiguration
    @Mapping(target = "items", ignore = true)
    Promotion toPromotion(PromotionDto promotionDto);
}
