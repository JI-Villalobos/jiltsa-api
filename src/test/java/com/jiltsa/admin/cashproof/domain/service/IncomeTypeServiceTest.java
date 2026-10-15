package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeType;
import com.jiltsa.admin.cashproof.persistence.mapper.IncomeTypeMapper;
import com.jiltsa.admin.cashproof.persistence.repository.IncomeTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncomeTypeServiceTest {
    @Mock
    private IncomeTypeRepository repository;
    private IncomeTypeService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new IncomeTypeService(repository, Mappers.getMapper(IncomeTypeMapper.class));
    }

    @Test
    void typesAreMappedFromTheRepository() {
        when(repository.findAll()).thenReturn(List.of(new IncomeType("PRONTIPAGOS"), new IncomeType("MEDICAMENTO")));

        assertThat(serviceUnderTest.getIncomeTypes()).extracting(IncomeTypeDto::getType).containsExactly("PRONTIPAGOS", "MEDICAMENTO");
    }

    @Test
    void createSavesTheType() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        IncomeTypeDto created = serviceUnderTest.createIncomeType(new IncomeTypeDto(null, "other"));

        ArgumentCaptor<IncomeType> saved = ArgumentCaptor.forClass(IncomeType.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getType()).isEqualTo("other");
        assertThat(created.getType()).isEqualTo("other");
    }
}
