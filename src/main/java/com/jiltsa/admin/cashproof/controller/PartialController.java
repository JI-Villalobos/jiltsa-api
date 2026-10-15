package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.domain.service.PartialService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/partials")
@RequiredArgsConstructor
public class PartialController {
    private final PartialService service;

    @Operation(summary = "Get a partial payment of a credit sale")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No partial payment with that id")
    @GetMapping("/{partialId}")
    public PartialDto getPartial(@Parameter(description = "Partial payment id") @PathVariable("partialId") Integer partialId){
        return service.getPartial(partialId)
                .orElseThrow(() -> new ResourceNotFoundException("Partial", partialId));
    }

    @Operation(summary = "Register a partial payment of a credit sale")
    @PostMapping
    public PartialDto createPartial(@Valid @RequestBody PartialDto partialDto){
        return service.createPartial(partialDto);
    }
}
