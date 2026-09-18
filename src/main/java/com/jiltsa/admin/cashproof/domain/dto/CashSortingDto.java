package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record CashSortingDto(
        Integer id, @NotNull Integer branchId, @NotNull Integer accountingId, @NotNull LocalDateTime cashDate,
        @NotNull @PositiveOrZero Integer bt1000, @NotNull @PositiveOrZero Integer bt500, @NotNull @PositiveOrZero Integer bt200, @NotNull @PositiveOrZero Integer bt100, @NotNull @PositiveOrZero Integer bt50,
        @NotNull @PositiveOrZero Integer bt20, @NotNull @PositiveOrZero Integer md20, @NotNull @PositiveOrZero Integer md10, @NotNull @PositiveOrZero Integer md5, @NotNull @PositiveOrZero Integer md2, @NotNull @PositiveOrZero Integer md1,
        @NotNull @PositiveOrZero Integer md005, @NotNull @PositiveOrZero Integer bls10, @NotNull @PositiveOrZero Integer bls5, @NotNull @PositiveOrZero Integer bls2, @NotNull @PositiveOrZero Integer bls1
) {
}
