package com.jiltsa.admin.operativity.persistence.mapper;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;
import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperativeExpenseMapper {
    OperativeExpenseDto toOperativeExpenseDto(OperativeExpense operativeExpense);
    List<OperativeExpenseDto> toOperativeExpenseDtoList(List<OperativeExpense> operativeExpenses);

    @InheritInverseConfiguration
    OperativeExpense toOperativeExpense(OperativeExpenseDto operativeExpenseDto);
}
