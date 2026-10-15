package com.jiltsa.admin.sales.persistence.mapper;

import com.jiltsa.admin.sales.domain.dto.CreateProductDto;
import com.jiltsa.admin.sales.persistence.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    Product toProduct(CreateProductDto createProductDto);
    List<Product> toProductList(List<CreateProductDto> createProductDtos);
}
