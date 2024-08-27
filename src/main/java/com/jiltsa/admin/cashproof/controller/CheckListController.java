package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.domain.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/check-list")
@RequiredArgsConstructor
public class CheckListController {
    private final CheckListService service;

    @GetMapping("/{checkListId}")
    public Optional<CheckListDto> getCheckList(@PathVariable(name = "checkListId") Integer checkListId){
        return service.getCheckList(checkListId);
    }

    @PostMapping
    public CheckListDto createCheckList(@RequestBody CheckListDto checkListDto){
        return service.createCheckList(checkListDto);
    }
}
