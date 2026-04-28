package com.jiltsa.admin.cashproof.domain.dto;

import java.time.LocalDateTime;

public record CashSortingDto(
        Integer id, Integer branchId, Integer accountingId, LocalDateTime cashDate,
        Integer bt1000, Integer bt500, Integer bt200, Integer bt100, Integer bt50,
        Integer bt20, Integer md20, Integer md10, Integer md5, Integer md2, Integer md1,
        Integer md005, Integer bls10, Integer bls5, Integer bls2, Integer bls1
) {
}
