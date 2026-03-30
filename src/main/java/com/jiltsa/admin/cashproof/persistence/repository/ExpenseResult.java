package com.jiltsa.admin.cashproof.persistence.repository;

public interface ExpenseResult {
    String getType();
    Integer getBranchId();
    Integer getExpenseTypeId();
    Double getTotal();
}
