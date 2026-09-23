package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.domain.dto.CreateSaleDto;
import com.jiltsa.admin.sales.domain.dto.SaleBatchResultDto;
import com.jiltsa.admin.sales.domain.service.SaleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {
    static final int MAX_BATCH_SIZE = 5000;

    private final SaleService service;

    /** Sales arrive in batches (a POS export); every line is validated before any is stored. */
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public SaleBatchResultDto createSales(
            @RequestBody @NotEmpty @Size(max = MAX_BATCH_SIZE) List<@Valid @NotNull CreateSaleDto> sales){
        return service.createSales(sales);
    }
}
