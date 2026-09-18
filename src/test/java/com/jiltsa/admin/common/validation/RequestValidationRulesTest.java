package com.jiltsa.admin.common.validation;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.orders.domain.dto.OrderDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/** The request-body rules: required strings must not be blank, movements must be positive, stock figures may be zero. */
class RequestValidationRulesTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static Set<String> violatedFields(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(java.util.stream.Collectors.toSet());
    }

    @Test
    void blankStringsAreRejected() {
        assertThat(violatedFields(validator.validate(new BranchDto(null, "   ", true)))).containsExactly("name");
        assertThat(violatedFields(validator.validate(new CreateIncomeRegistryDto(null, 1, 1, 10.0, "")))).containsExactly("tag");
    }

    @Test
    void movementsMustBePositive() {
        assertThat(violatedFields(validator.validate(new PartialDto(null, 1, LocalDateTime.now(), -5.0)))).containsExactly("amount");
        assertThat(violatedFields(validator.validate(new PartialDto(null, 1, LocalDateTime.now(), 0.0)))).containsExactly("amount");
        assertThat(validator.validate(new PartialDto(null, 1, LocalDateTime.now(), 0.01))).isEmpty();
    }

    @Test
    void costsMayBeZeroButNotNegative() {
        OrderDto unpriced = new OrderDto(null, 1, 1, LocalDate.now(), 100.0, 0.0, 1, true, null);
        assertThat(validator.validate(unpriced)).isEmpty();

        OrderDto negative = new OrderDto(null, 1, 1, LocalDate.now(), 100.0, -1.0, 1, true, null);
        assertThat(violatedFields(validator.validate(negative))).containsExactly("realCost");
    }

    @Test
    void cashCountsMayBeZeroButNotNegative() {
        CashSortingDto empty = new CashSortingDto(null, 1, 1, LocalDateTime.now(),
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(validator.validate(empty)).isEmpty();

        CashSortingDto negative = new CashSortingDto(null, 1, 1, LocalDateTime.now(),
                -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(violatedFields(validator.validate(negative))).containsExactly("bt1000");
    }
}
