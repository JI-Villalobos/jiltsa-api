package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.domain.repository.CheckListDtoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckListService {
    private final CheckListDtoRepository checkListDtoRepository;

    public Optional<CheckListDto> getCheckList(Integer checkListId){
        return checkListDtoRepository.getCheckList(checkListId);
    }

    @Transactional
    public CheckListDto createCheckList(CheckListDto checkListDto){
        return checkListDtoRepository.createCheckList(checkListDto);
    }
}
