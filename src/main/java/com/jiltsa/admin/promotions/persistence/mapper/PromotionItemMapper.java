package com.jiltsa.admin.promotions.persistence.mapper;

import com.jiltsa.admin.promotions.domain.dto.PromotionItemDto;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionItemMapper {
    PromotionItemDto toPromotionItemDto(PromotionItem item);
    List<PromotionItemDto> toPromotionItemDtoList(List<PromotionItem> items);

    @InheritInverseConfiguration
    @Mapping(target = "promotion", ignore = true)
    PromotionItem toPromotionItem(PromotionItemDto itemDto);

    @InheritInverseConfiguration
    @Mapping(target = "promotion", ignore = true)
    List<PromotionItem> toPromotionItemList(List<PromotionItemDto> itemDtoList);
}
