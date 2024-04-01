package com.jiltsa.admin.billing.controller;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.domain.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            @RequestParam(defaultValue = "7") int elements,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection){
        return service.getPendingBills(page, elements, sortBy, sortDirection);
    }

    @GetMapping("/{id}")
    public Optional<BillDto> getBill(@PathVariable("id") Integer id){
        return service.getBill(id);
    }

    @PostMapping
    public BillDto createBill(@RequestBody BillDto billDto){
        return service.createBill(billDto);
    }

    @PostMapping("/save-all")
    public List<BillDto> createBills(@RequestBody List<BillDto> billDtoList){
        return service.createBills(billDtoList);
    }

    @PutMapping
    public List<BillDto> updateBills(@RequestBody List<BillDto> billDtoList){
        return service.updateBills(billDtoList);
    }
}
