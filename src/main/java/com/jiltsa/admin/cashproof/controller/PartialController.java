package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.domain.service.PartialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/partials")
@RequiredArgsConstructor
public class PartialController {
    private final PartialService service;

    @GetMapping("/{partialId}")
    public Optional<PartialDto> getPartial(@PathVariable("partialId") Integer partialId){
        return service.getPartial(partialId);
    }

    @PostMapping
    public PartialDto createPartial(@RequestBody PartialDto partialDto){
        return service.createPartial(partialDto);
    }
}
