package com.jiltsa.admin.operativity.persistence.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/** getTotalExpenses replaced the sp_total_expenses procedure and must return what it returned. */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class OperativeExpenseRepositoryTest {
    private static final LocalDateTime START = LocalDateTime.of(2026, 3, 1, 0, 0);
    private static final LocalDateTime END = LocalDateTime.of(2026, 3, 31, 23, 59, 59);

    @Autowired
    OperativeExpenseRepository repositoryUnderTest;
    @Autowired
    JdbcTemplate jdbc;

    private int branch;
    private int branchWithoutExpenses;

    @BeforeAll
    void setUp() {
        branch = insert("branches", Map.of("name", "Coyote", "is_active", true));
        int otherBranch = insert("branches", Map.of("name", "Nazas", "is_active", true));
        branchWithoutExpenses = insert("branches", Map.of("name", "Pen", "is_active", true));
        int expenseType = insert("expense_type", Map.of("type", "supplies"));

        operativeExpense(branch, 100.004, START);
        operativeExpense(branch, 20.0, END);
        operativeExpense(branch, 999.0, END.plusSeconds(1));
        operativeExpense(otherBranch, 999.0, START);

        expense(account(branch, START.plusDays(10)), expenseType, 50.006);
        expense(account(branch, START.minusSeconds(1)), expenseType, 999.0);
        expense(account(otherBranch, START.plusDays(10)), expenseType, 999.0);
    }

    @Test
    void addsBothKindsOfExpensesWithinTheRangeRoundedToCents() {
        // 100.004 + 20 -> 120.00 and 50.006 -> 50.01, as the procedure's DECIMAL(10,2) variables did
        assertThat(repositoryUnderTest.getTotalExpenses(branch, START, END)).isEqualTo(170.01);
    }

    @Test
    void aBranchWithoutExpensesTotalsZero() {
        assertThat(repositoryUnderTest.getTotalExpenses(branchWithoutExpenses, START, END)).isEqualTo(0.0);
    }

    private void operativeExpense(int branchId, double amount, LocalDateTime date) {
        insert("operative_expense", Map.of("branch_id", branchId, "amount", amount, "expense_date", date,
                "category", "c", "concept", "c"));
    }

    private int account(int branchId, LocalDateTime date) {
        int seller = insert("seller", Map.of("branch_id", branchId, "full_name", "Diana", "is_active", true));
        return insert("accounts", Map.of("branch_id", branchId, "seller_id", seller, "date", date));
    }

    private void expense(int account, int expenseType, double amount) {
        insert("expense_registry", Map.of("accounting_id", account, "expense_type_id", expenseType, "amount", amount,
                "description", "d"));
    }

    private int insert(String table, Map<String, Object> row) {
        return new SimpleJdbcInsert(jdbc).withTableName(table).usingGeneratedKeyColumns("id").executeAndReturnKey(row).intValue();
    }
}
