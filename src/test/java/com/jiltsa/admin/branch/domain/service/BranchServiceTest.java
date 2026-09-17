package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.mapper.BranchMapper;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {
    @Mock
    private BranchRepository repository;
    private BranchDService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new BranchDService(repository, Mappers.getMapper(BranchMapper.class));
    }

    @Test
    void createBranchSavesNameAndActiveFlag() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        BranchDto created = serviceUnderTest.createBranch(new BranchDto(null, "peñon", true));

        ArgumentCaptor<Branch> saved = ArgumentCaptor.forClass(Branch.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getName()).isEqualTo("peñon");
        assertThat(saved.getValue().getIsActive()).isTrue();
        assertThat(created.getName()).isEqualTo("peñon");
    }

    @Test
    void getAllMapsEveryBranch() {
        when(repository.findAll()).thenReturn(List.of(new Branch("nazas", true), new Branch("coyote", false)));

        assertThat(serviceUnderTest.getAll()).extracting(BranchDto::getName).containsExactly("nazas", "coyote");
    }

    @Test
    void getByIdIsEmptyWhenMissing() {
        when(repository.findById(42)).thenReturn(Optional.empty());

        assertThat(serviceUnderTest.getById(42)).isEmpty();
    }
}
