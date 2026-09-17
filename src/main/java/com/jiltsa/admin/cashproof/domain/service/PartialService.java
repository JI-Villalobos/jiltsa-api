package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.persistence.entity.Partial;
import com.jiltsa.admin.cashproof.persistence.mapper.PartialMapper;
import com.jiltsa.admin.cashproof.persistence.repository.PartialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartialService {
    private final PartialRepository repository;
    private final PartialMapper mapper;

    public Optional<PartialDto> getPartial(Integer partialId) {
        return repository.findById(partialId).map(mapper::toPartialDto);
    }

    @Transactional
    public PartialDto createPartial(PartialDto partialDto) {
        Partial partial = mapper.toPartial(partialDto);

        return mapper.toPartialDto(repository.save(partial));
    }
}
