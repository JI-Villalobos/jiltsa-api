package com.jiltsa.admin.billing.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.domain.service.BillService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService service;

    @GetMapping
    public Page<BillDto> getAllBills(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int elements){
        return service.getAllBills(page, elements);
    }

    @GetMapping("/pending")
    public Page<BillDto> getPendingBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int elements,
            @RequestParam(defaultValue = "limitPaymentDate") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection){
        return service.getPendingBills(page, elements, sortBy, sortDirection);
    }

    @GetMapping("/after")
    public Page<BillDto> getBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int elements,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection){
        return service.getBillsAfterADate(page, elements, sortBy, sortDirection);
    }

    @GetMapping("/between")
    public Page<BillDto> getBillsBetween(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime finishDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int elements,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection){
        return service.getBillsBetweenDates(page, elements, sortBy, sortDirection, startDate, finishDate);
    }

    @GetMapping("/{id}")
    public BillDto getBill(@PathVariable("id") Integer id){
        return service.getBill(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", id));
    }

    @PostMapping
    public BillDto createBill(@Valid @RequestBody BillDto billDto){
        return service.createBill(billDto);
    }

    @PostMapping("/save-all")
    public List<BillDto> createBills(@RequestBody List<@Valid BillDto> billDtoList){
        return service.createBills(billDtoList);
    }

    @PutMapping
    public List<BillDto> updateBills(@RequestBody List<@Valid BillDto> billDtoList){
        return service.updateBills(billDtoList);
    }

    @PutMapping("/update")
    public  BillDto updateBill(@Valid @RequestBody BillDto billDto){
        return service.updateBill(billDto);
    }
}
