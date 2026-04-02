package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.operativity.domain.dto.AverageSalesResultDto;
import com.jiltsa.admin.operativity.projection.AverageSalesProjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jiltsa/api/v1/average-sales")
@RequiredArgsConstructor
public class AverageSalesProjectionController {
    private final AverageSalesProjectionService service;

    @GetMapping("/branch/{branchId}")
    public AverageSalesResultDto getAverageSales(@PathVariable("branchId") Integer branchId){
        return service.getAverageSales(branchId);
    }
}
