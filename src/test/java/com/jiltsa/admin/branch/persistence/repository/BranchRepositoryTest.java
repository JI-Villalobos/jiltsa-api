package com.jiltsa.admin.branch.persistence.repository;

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

/** getTotalBalance replaced the get_totals procedure and must return what it returned. */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class BranchRepositoryTest {
    @Autowired
    BranchRepository repositoryUnderTest;
    @Autowired
    JdbcTemplate jdbc;

    private int branch;
    private int quietBranch;
    private int unconfiguredBranch;

    @BeforeAll
    void setUp() {
        branch = insert("branches", Map.of("name", "Coyote", "is_active", true));
        int otherBranch = insert("branches", Map.of("name", "Nazas", "is_active", true));
        quietBranch = insert("branches", Map.of("name", "Pen", "is_active", true));
        unconfiguredBranch = insert("branches", Map.of("name", "New", "is_active", true));
        configure(branch, 1000.0);
        configure(otherBranch, 5000.0);
        configure(quietBranch, 750.0);

        int incomeType = insert("income_type", Map.of("type", "sales"));
        int expenseType = insert("expense_type", Map.of("type", "supplies"));

        int account = account(branch);
        income(account, incomeType, 300.0);
        income(account, incomeType, 200.5);
        expense(account, expenseType, 50.25);
        withdrawal(branch, 100.0);

        // another branch's movements must not leak into the balance
        int otherAccount = account(otherBranch);
        income(otherAccount, incomeType, 999.0);
        expense(otherAccount, expenseType, 999.0);
        withdrawal(otherBranch, 999.0);
    }

    @Test
    void balanceIsInitialBalancePlusIncomesMinusExpensesAndWithdrawals() {
        assertThat(repositoryUnderTest.getTotalBalance(branch)).isEqualTo(1000.0 + 300.0 + 200.5 - 50.25 - 100.0);
    }

    @Test
    void aBranchWithoutMovementsHasItsInitialBalance() {
        assertThat(repositoryUnderTest.getTotalBalance(quietBranch)).isEqualTo(750.0);
    }

    @Test
    void aBranchWithoutConfigurationHasNoBalance() {
        assertThat(repositoryUnderTest.getTotalBalance(unconfiguredBranch)).isNull();
    }

    private void configure(int branchId, double initialBalance) {
        insert("branch_config", Map.of("branch_id", branchId, "initial_balance", initialBalance,
                "can_edit_account", false, "can_open_outdated_account", false, "profile", "PHARMACY"));
    }

    private int account(int branchId) {
        int seller = insert("seller", Map.of("branch_id", branchId, "full_name", "Diana", "is_active", true));
        return insert("accounts", Map.of("branch_id", branchId, "seller_id", seller, "date", LocalDateTime.now()));
    }

    private void income(int account, int incomeType, double amount) {
        insert("cash_registry", Map.of("accounting_id", account, "income_type_id", incomeType, "amount", amount, "tag", "t"));
    }

    private void expense(int account, int expenseType, double amount) {
        insert("expense_registry", Map.of("accounting_id", account, "expense_type_id", expenseType, "amount", amount,
                "description", "d"));
    }

    private void withdrawal(int branchId, double amount) {
        insert("cash_withdrawals", Map.of("branch_id", branchId, "amount", amount, "concept", "c", "seller_name", "Diana",
                "date", LocalDateTime.now()));
    }

    private int insert(String table, Map<String, Object> row) {
        return new SimpleJdbcInsert(jdbc).withTableName(table).usingGeneratedKeyColumns("id").executeAndReturnKey(row).intValue();
    }
}
