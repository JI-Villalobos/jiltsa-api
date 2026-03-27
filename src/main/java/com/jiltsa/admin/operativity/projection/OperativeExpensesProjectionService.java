package com.jiltsa.admin.operativity.projection;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseProjectionDto;
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
}
