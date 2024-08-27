package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.domain.repository.CheckListDtoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckListService {
    private final CheckListDtoRepository checkListDtoRepository;

    public Optional<CheckListDto> getCheckList(Integer checkListId){
        return checkListDtoRepository.getCheckList(checkListId);
    }

    public CheckListDto createCheckList(CheckListDto checkListDto){
        return checkListDtoRepository.createCheckList(checkListDto);
    }
}
