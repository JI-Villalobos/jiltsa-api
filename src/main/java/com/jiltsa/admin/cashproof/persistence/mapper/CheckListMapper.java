package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CheckListDto;
import com.jiltsa.admin.cashproof.persistence.entity.CheckList;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CheckListMapper {
    CheckListDto toCheckListDto(CheckList checkList);

    @InheritInverseConfiguration
    CheckList toCheckList(CheckListDto checkListDto);
}
