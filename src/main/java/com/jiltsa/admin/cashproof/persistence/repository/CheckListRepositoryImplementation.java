package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.domain.repository.CheckListDtoRepository;
import com.jiltsa.admin.cashproof.persistence.entity.CheckList;
import com.jiltsa.admin.cashproof.persistence.mapper.CheckListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CheckListRepositoryImplementation implements CheckListDtoRepository {
    private final CheckListRepository repository;
    private final CheckListMapper mapper;

    @Override
    public Optional<CheckListDto> getCheckList(Integer checkListId) {
        return repository.findById(checkListId).map(mapper::toCheckListDto);
    }

    @Override
    public CheckListDto createCheckList(CheckListDto checkListDto) {
        CheckList checkList = mapper.toCheckList(checkListDto);

        return mapper.toCheckListDto(repository.save(checkList));
    }
}
