package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.PartialDto;

import java.util.Optional;

public interface PartialDRepository {
    Optional<PartialDto> getPartial(Integer partialId);
    PartialDto createPartial(PartialDto partialDto);
}
