package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.operativity.domain.dto.BreakEvenPointDto;
import com.jiltsa.admin.operativity.domain.service.BreakEventPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/bep")
@RequiredArgsConstructor
public class BreakEvenPointController {
    private final BreakEventPointService service;

    @PostMapping
    public BreakEvenPointDto saveBEP(@RequestBody BreakEvenPointDto breakEvenPointDto){
        return service.saveBEP(breakEvenPointDto);
    }

    @PutMapping
    public BreakEvenPointDto updateBEP(@RequestBody BreakEvenPointDto breakEvenPointDto){
        return service.saveBEP(breakEvenPointDto);
    }

    @GetMapping("/branch/{branchId}")
    public Optional<BreakEvenPointDto> getBEP(@PathVariable("branchId") Integer branchId){
        return service.getBreakEvenPointDto(branchId);
    }
}
