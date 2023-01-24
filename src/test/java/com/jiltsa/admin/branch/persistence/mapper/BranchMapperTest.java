package com.jiltsa.admin.branch.persistence.mapper;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BranchMapperTest {

    @Test
    void shouldMapToBranchDto() {
        //given
        BranchMapper mapper = Mappers.getMapper(BranchMapper.class);
        List<Seller> sellers = new ArrayList<>();
        Branch branch = new Branch(1, "nazas", true, sellers);

        //when
        BranchDto branchDto = mapper.toBranchDto(branch);

        //then
        assertThat(branchDto).isNotNull();
        assertThat(branchDto.getName()).isEqualTo("nazas");
        assertThat(branchDto.getIsActive()).isTrue();
    }

    @Test
    @Disabled
    void toBranchesDto() {
    }

    @Test
    void shouldMapToBranch() {
        //given
        BranchMapper mapper = Mappers.getMapper(BranchMapper.class);
        BranchDto branchDto = new BranchDto(1, "nazas", true);

        //when
        Branch branch = mapper.toBranch(branchDto);

        //then
        assertThat(branch).isNotNull();
        assertThat(branch.getName()).isEqualTo("nazas");
        assertThat(branch.getIsActive()).isTrue();
    }

    @Test
    @Disabled
    void toBranches() {
    }
}