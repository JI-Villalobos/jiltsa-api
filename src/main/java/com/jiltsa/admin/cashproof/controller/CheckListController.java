package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.domain.service.CheckListService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/check-list")
@RequiredArgsConstructor
public class CheckListController {
    private final CheckListService service;

    @Operation(summary = "Get a check list")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No check list with that id")
    @GetMapping("/{checkListId}")
    public CheckListDto getCheckList(@Parameter(description = "Check list id") @PathVariable(name = "checkListId") Integer checkListId){
        return service.getCheckList(checkListId)
                .orElseThrow(() -> new ResourceNotFoundException("CheckList", checkListId));
    }

    @Operation(summary = "Register a branch check list", description = "Opening or closing inspection of cash, equipment and premises.")
    @PostMapping
    public CheckListDto createCheckList(@Valid @RequestBody CheckListDto checkListDto){
        return service.createCheckList(checkListDto);
    }
}
