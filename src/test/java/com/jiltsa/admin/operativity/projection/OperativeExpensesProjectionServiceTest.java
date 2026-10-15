package com.jiltsa.admin.operativity.projection;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseRegistryService;
import com.jiltsa.admin.operativity.PharmacyProperties;
import com.jiltsa.admin.operativity.domain.dto.OperativeCostDto;
import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import com.jiltsa.admin.operativity.persistence.repository.OperativeExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperativeExpensesProjectionServiceTest {
    private static final PharmacyProperties PHARMACY = new PharmacyProperties(6, "PROVEEDORES DE MEDICAMENTO");

    @Mock private OperativeExpenseRepository operativeExpenseRepository;
    @Mock private ExpenseRegistryService expenseRegistryService;

    @Test
    void operativeCostAddsMedicineSuppliersToMedicinePaidFromTheRegistry() {
        LocalDateTime from = LocalDateTime.now().minusMonths(1), to = LocalDateTime.now();
        when(operativeExpenseRepository.findByBranchIdAndExpenseDateBetween(1, from, to)).thenReturn(List.of(
                expense("PROVEEDORES DE MEDICAMENTO", 1000.0),
                expense("PROVEEDORES DE MEDICAMENTO", 250.0),
                expense("SUELDOS", 9999.0)));
        when(expenseRegistryService.getExpenseReportByType(1, 6, from, to)).thenReturn(List.of(
                new ExpenseReportDto("MEDICAMENTO", 1, 6, 300.0)));

        OperativeCostDto cost = new OperativeExpensesProjectionService(operativeExpenseRepository, expenseRegistryService, PHARMACY)
                .getOperativeCost(1, from, to);

        assertThat(cost.branchId()).isEqualTo(1);
        assertThat(cost.cost()).isEqualTo(1000.0 + 250.0 + 300.0);
    }

    private static OperativeExpense expense(String category, double amount) {
        OperativeExpense e = new OperativeExpense();
        e.setBranchId(1);
        e.setCategory(category);
        e.setAmount(amount);
        e.setConcept("x");
        e.setExpenseDate(LocalDateTime.now());
        return e;
    }
}
