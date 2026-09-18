package com.jiltsa.admin.operativity.projection;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseRegistryService;
import com.jiltsa.admin.operativity.PharmacyProperties;
import com.jiltsa.admin.operativity.domain.dto.OperativeCostDto;
import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseProjectionDto;
import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseTotalsDto;
import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import com.jiltsa.admin.operativity.persistence.repository.OperativeExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OperativeExpensesProjectionService {
    private final OperativeExpenseRepository operativeExpenseRepository;
    private final ExpenseRegistryService expenseRegistryService;
    private final PharmacyProperties pharmacy;

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
                .filter(operativeExpense -> operativeExpense.getCategory().equals(pharmacy.operativeExpenseCategory()))
                .toList();

        Double operative = pharmacyExpense.stream().reduce(0.0, (acc, curr) -> acc + curr.getAmount(), Double::sum);

        List<ExpenseReportDto> expenseResults = expenseRegistryService.getExpenseReportByType(
                branchId, pharmacy.expenseTypeId(), initialDate, finalDate);

        Double local = expenseResults.stream().reduce(0.0, (acc, curr) -> acc + curr.total(), Double::sum);

        var cost = operative + local;

        return new OperativeCostDto(branchId, cost);
    }

    public OperativeExpenseTotalsDto getTotalExpenses(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        Double totals = operativeExpenseRepository.getTotalExpenses(branchId, initialDate, finalDate);

        return new OperativeExpenseTotalsDto(branchId, totals);
    }
}
