package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseType;
import com.jiltsa.admin.cashproof.persistence.mapper.ExpenseTypeMapper;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseTypeRepository;
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
class ExpenseTypeServiceTest {
    @Mock
    private ExpenseTypeRepository repository;
    private ExpenseTypeService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new ExpenseTypeService(repository, Mappers.getMapper(ExpenseTypeMapper.class));
    }

    @Test
    void typesAreMappedFromTheRepository() {
        when(repository.findAll()).thenReturn(List.of(new ExpenseType("SUELDOS"), new ExpenseType("RENTA")));

        assertThat(serviceUnderTest.getExpenseTypes()).extracting(ExpenseTypeDto::getType).containsExactly("SUELDOS", "RENTA");
    }

    @Test
    void createSavesTheType() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ExpenseTypeDto created = serviceUnderTest.createExpenseType(new ExpenseTypeDto(null, "other"));

        ArgumentCaptor<ExpenseType> saved = ArgumentCaptor.forClass(ExpenseType.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getType()).isEqualTo("other");
        assertThat(created.getType()).isEqualTo("other");
    }
}
