package com.jiltsa.admin.operativity.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.operativity.domain.dto.BreakEvenPointDto;
import com.jiltsa.admin.operativity.domain.service.BreakEvenPointService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/bep")
@RequiredArgsConstructor
public class BreakEvenPointController {
    private final BreakEvenPointService service;

    @PostMapping
    public BreakEvenPointDto saveBEP(@Valid @RequestBody BreakEvenPointDto breakEvenPointDto){
        return service.saveBEP(breakEvenPointDto);
    }

    @PutMapping
    public BreakEvenPointDto updateBEP(@Valid @RequestBody BreakEvenPointDto breakEvenPointDto){
        return service.saveBEP(breakEvenPointDto);
    }

    @GetMapping("/branch/{branchId}")
    public BreakEvenPointDto getBEP(@PathVariable("branchId") Integer branchId){
        return service.getBreakEvenPoint(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("BreakEvenPoint", branchId));
    }
}
