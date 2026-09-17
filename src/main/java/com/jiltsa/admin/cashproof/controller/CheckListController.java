package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.domain.service.CheckListService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/check-list")
@RequiredArgsConstructor
public class CheckListController {
    private final CheckListService service;

    @GetMapping("/{checkListId}")
    public CheckListDto getCheckList(@PathVariable(name = "checkListId") Integer checkListId){
        return service.getCheckList(checkListId)
                .orElseThrow(() -> new ResourceNotFoundException("CheckList", checkListId));
    }

    @PostMapping
    public CheckListDto createCheckList(@RequestBody CheckListDto checkListDto){
        return service.createCheckList(checkListDto);
    }
}
