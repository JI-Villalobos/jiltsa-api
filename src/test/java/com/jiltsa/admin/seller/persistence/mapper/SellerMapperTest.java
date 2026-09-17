package com.jiltsa.admin.seller.persistence.mapper;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SellerMapperTest {

    @Test
    void shouldMapToSellerDto() {
        //given
        SellerMapper mapper = Mappers.getMapper(SellerMapper.class);
        Branch branch = new Branch();
        Seller seller = new Seller(1, "Diana Path", 1, "1234", true, branch);

        //when
        SellerDto sellerDto = mapper.toSellerDto(seller);

        //then
        assertThat(sellerDto).isNotNull();
        assertThat(sellerDto.getIsActive()).isTrue();
    }

    @Test
    void shouldMapToSeller() {
        //given
        SellerMapper mapper = Mappers.getMapper(SellerMapper.class);
        SellerDto sellerDto = new SellerDto(1, "Diana Path", 1, true);
        String defaultPass = "1234";

        //when
        Seller seller = mapper.toSeller(sellerDto);

        //then
        assertThat(seller).isNotNull();
        assertThat(seller.getBranch()).isNull();
        assertThat(seller.getFullName()).isEqualTo("Diana Path");
        assertThat(seller.getBranchId()).isEqualTo(1);
        assertThat(seller.getIsActive()).isTrue();
        assertThat(seller.getPassword()).isEqualTo(defaultPass);
    }
}