package com.jiltsa.admin.operativity.projection;

import com.jiltsa.admin.cashproof.persistence.repository.ExpenseRegistryRepository;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseResult;
import com.jiltsa.admin.operativity.domain.dto.OperativeCostDto;
import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseProjectionDto;
import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseTotalsDto;
import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import com.jiltsa.admin.operativity.persistence.repository.OperativeExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperativeExpensesProjectionService {
    public final List<String> expenseCategories = Arrays.asList(
            "PROVEEDORES DE MEDICAMENTO",
            "PROVEEDORES EXTERNOS",
            "PROVEEDORES EN PUNTO DE VENTA",
            "SUELDOS",
            "GASTOS ADMINISTRATIVOS",
            "PAGO DE SERVICIOS",
            "OTROS GASTOS"
    );

    private final OperativeExpenseRepository operativeExpenseRepository;
    private final ExpenseRegistryRepository expenseRegistryRepository;

    public OperativeExpenseProjectionDto getExpensesProjection (Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        List<OperativeExpense> operativeExpenses = operativeExpenseRepository.findByBranchIdAndExpenseDateBetween(branchId, initialDate, finalDate);

        Map<String, Double> operativeExpensesProjection = operativeExpenses.stream().collect(
                Collectors.groupingBy(
                        OperativeExpense::getCategory,
                        Collectors.summingDouble(OperativeExpense::getAmount)
                )
        );

        return new OperativeExpenseProjectionDto(operativeExpensesProjection);
    }

    public OperativeCostDto getOperativeCost(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        List<OperativeExpense> operativeExpenses = operativeExpenseRepository.findByBranchIdAndExpenseDateBetween(branchId, initialDate, finalDate);
        List<OperativeExpense> pharmacyExpense = operativeExpenses
                .stream()
                .filter(operativeExpense -> operativeExpense.getCategory().equals("PROVEEDORES DE MEDICAMENTO"))
                .toList();

        Double operative = pharmacyExpense.stream().reduce(0.0, (acc, curr) -> acc + curr.getAmount(), Double::sum);

        List<ExpenseResult> expenseResults = expenseRegistryRepository.getPharmacyExpenseReport(branchId, initialDate, finalDate);

        Double local = expenseResults.stream().reduce(0.0, (acc, curr) -> acc + curr.getTotal(), Double::sum);

        var cost = operative + local;

        return new OperativeCostDto(branchId, cost);
    }

    public OperativeExpenseTotalsDto getTotalExpenses(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        Double totals = operativeExpenseRepository.getTotalExpenses(branchId, initialDate, finalDate);

        return new OperativeExpenseTotalsDto(branchId, totals);
    }
}
