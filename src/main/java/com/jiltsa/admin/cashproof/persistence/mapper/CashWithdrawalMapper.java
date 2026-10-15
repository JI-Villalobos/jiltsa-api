package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;


import java.util.List;

/** The branch name comes from the branch module, so the caller passes it in. */
@Mapper(componentModel = "spring")
public interface CashWithdrawalMapper {
    default Page<CashWithdrawalDto> toCashWithdrawalDtoPage(Page<CashWithdrawal> cashWithdrawals, String branchName){
        return cashWithdrawals.map(cashWithdrawal -> toCashWithdrawalDto(cashWithdrawal, branchName));
    }
    default List<CashWithdrawalDto> toCashWithdrawalDtoList(List<CashWithdrawal> cashWithdrawalList, String branchName){
        return cashWithdrawalList.stream().map(cashWithdrawal -> toCashWithdrawalDto(cashWithdrawal, branchName)).toList();
    }

    @Mapping(target = "branch", source = "branchName")
    CashWithdrawalDto toCashWithdrawalDto(CashWithdrawal cashWithdrawal, String branchName);

    @Mapping(target = "branch", source = "branchName")
    CreateCashWithdrawalDto toCreateCashWithdrawalDto(CashWithdrawal cashWithdrawal, String branchName);

    @Mapping(target = "date", ignore = true)
    CashWithdrawal toCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto);

    CashWithdrawal toCashWithdrawal(CashWithdrawalDto cashWithdrawalDto);
}
