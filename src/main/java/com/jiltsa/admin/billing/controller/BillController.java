package com.jiltsa.admin.billing.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.domain.service.BillService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService service;

    @Operation(summary = "List all bills")
    @GetMapping
    public Page<BillDto> getAllBills(
            @PageableDefault(size = 12) Pageable pageable){
        return service.getAllBills(pageable);
    }

    @Operation(summary = "List unpaid bills", description = "Sorted by payment due date unless sortBy says otherwise.")
    @GetMapping("/pending")
    public Page<BillDto> getPendingBills(
            @PageableDefault(size = 12, sort = "limitPaymentDate") Pageable pageable){
        return service.getPendingBills(pageable);
    }

    @Operation(summary = "List bills from the last four months")
    @GetMapping("/after")
    public Page<BillDto> getBills(
            @PageableDefault(size = 12, sort = "date") Pageable pageable){
        return service.getRecentBills(pageable);
    }

    @Operation(summary = "List bills dated within a range")
    @GetMapping("/between")
    public Page<BillDto> getBillsBetween(
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime startDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finishDate,
            @PageableDefault(size = 12, sort = "date") Pageable pageable){
        return service.getBillsBetweenDates(pageable, startDate, finishDate);
    }

    @Operation(summary = "Get a bill")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No bill with that id")
    @GetMapping("/{id}")
    public BillDto getBill(@Parameter(description = "Bill id") @PathVariable("id") Integer id){
        return service.getBill(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", id));
    }

    @Operation(summary = "Create a bill")
    @PostMapping
    public BillDto createBill(@Valid @RequestBody BillDto billDto){
        return service.createBill(billDto);
    }

    @Operation(summary = "Create several bills")
    @PostMapping("/save-all")
    public List<BillDto> createBills(@RequestBody List<@Valid BillDto> billDtoList){
        return service.createBills(billDtoList);
    }

    @Operation(summary = "Update several bills")
    @PutMapping
    public List<BillDto> updateBills(@RequestBody List<@Valid BillDto> billDtoList){
        return service.updateBills(billDtoList);
    }

    @Operation(summary = "Update a bill")
    @PutMapping("/update")
    public  BillDto updateBill(@Valid @RequestBody BillDto billDto){
        return service.updateBill(billDto);
    }
}
