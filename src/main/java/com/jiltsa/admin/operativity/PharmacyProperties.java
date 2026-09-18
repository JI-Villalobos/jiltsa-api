package com.jiltsa.admin.operativity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

/**
 * How "medicine purchases" are identified in the two places they are recorded,
 * which together make up the pharmacy operative cost:
 * <ul>
 *   <li>{@code app.pharmacy.expense-type-id}: the {@code expense_type} row used for
 *       medicine purchases paid from the branch cash registry (env APP_PHARMACY_EXPENSE_TYPE_ID).</li>
 *   <li>{@code app.pharmacy.operative-expense-category}: the {@code operative_expense.category}
 *       value for medicine suppliers (env APP_PHARMACY_OPERATIVE_EXPENSE_CATEGORY).</li>
 * </ul>
 */
@Validated
@ConfigurationProperties("app.pharmacy")
public record PharmacyProperties(
        @NotNull @DefaultValue("6") Integer expenseTypeId,
        @NotBlank @DefaultValue("PROVEEDORES DE MEDICAMENTO") String operativeExpenseCategory
) {
}
