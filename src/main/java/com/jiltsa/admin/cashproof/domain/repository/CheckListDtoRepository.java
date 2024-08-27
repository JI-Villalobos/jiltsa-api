package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;

import java.util.Optional;

public interface CheckListDtoRepository {
    Optional<CheckListDto> getCheckList(Integer checkListId);
    CheckListDto createCheckList(CheckListDto checkListDto);
}
