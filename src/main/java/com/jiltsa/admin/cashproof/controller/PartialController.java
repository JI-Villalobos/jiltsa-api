package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.domain.service.PartialService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/partials")
@RequiredArgsConstructor
public class PartialController {
    private final PartialService service;

    @GetMapping("/{partialId}")
    public PartialDto getPartial(@PathVariable("partialId") Integer partialId){
        return service.getPartial(partialId)
                .orElseThrow(() -> new ResourceNotFoundException("Partial", partialId));
    }

    @PostMapping
    public PartialDto createPartial(@Valid @RequestBody PartialDto partialDto){
        return service.createPartial(partialDto);
    }
}
