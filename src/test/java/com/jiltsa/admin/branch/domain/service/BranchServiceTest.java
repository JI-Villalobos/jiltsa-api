package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.repository.BranchDRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private BranchDRepository repository;

    @InjectMocks
    private BranchDService serviceUnderTest;

    @BeforeEach
    void setUp(){
        serviceUnderTest = new BranchDService(repository);
    }

    @DisplayName("Create new branch service")
    @Test
    void shouldCreateBranch() {
        //given
        BranchDto branch = new BranchDto(1, "peñon", true);

        //when
        serviceUnderTest.createBranch(branch);

        //then
        ArgumentCaptor<BranchDto> branchDtoArgumentCaptor = ArgumentCaptor.forClass(BranchDto.class);
        verify(repository).createBranch(branchDtoArgumentCaptor.capture());
        BranchDto capureBranchDto = branchDtoArgumentCaptor.getValue();

        assertThat(capureBranchDto).isEqualTo(branch);
    }

    @DisplayName("Get all branches service")
    @Test
    void shouldGetAllBranches() {
        //when
        serviceUnderTest.getAll();

        //then
        verify(repository).getAll();
    }
}