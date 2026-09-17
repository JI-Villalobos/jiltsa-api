package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CashSortingDto(
        Integer id, @NotNull Integer branchId, @NotNull Integer accountingId, @NotNull LocalDateTime cashDate,
        @NotNull Integer bt1000, @NotNull Integer bt500, @NotNull Integer bt200, @NotNull Integer bt100, @NotNull Integer bt50,
        @NotNull Integer bt20, @NotNull Integer md20, @NotNull Integer md10, @NotNull Integer md5, @NotNull Integer md2, @NotNull Integer md1,
        @NotNull Integer md005, @NotNull Integer bls10, @NotNull Integer bls5, @NotNull Integer bls2, @NotNull Integer bls1
) {
}
