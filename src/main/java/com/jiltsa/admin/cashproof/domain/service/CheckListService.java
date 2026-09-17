package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.persistence.entity.CheckList;
import com.jiltsa.admin.cashproof.persistence.mapper.CheckListMapper;
import com.jiltsa.admin.cashproof.persistence.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckListService {
    private final CheckListRepository repository;
    private final CheckListMapper mapper;

    public Optional<CheckListDto> getCheckList(Integer checkListId) {
        return repository.findById(checkListId).map(mapper::toCheckListDto);
    }

    @Transactional
    public CheckListDto createCheckList(CheckListDto checkListDto) {
        CheckList checkList = mapper.toCheckList(checkListDto);

        return mapper.toCheckListDto(repository.save(checkList));
    }
}
