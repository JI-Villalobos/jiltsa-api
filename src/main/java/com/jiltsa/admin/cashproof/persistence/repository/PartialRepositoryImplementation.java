package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.domain.repository.PartialDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Partial;
import com.jiltsa.admin.cashproof.persistence.mapper.PartialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PartialRepositoryImplementation implements PartialDRepository {
    private final PartialRepository repository;
    private final PartialMapper mapper;

    @Override
    public Optional<PartialDto> getPartial(Integer partialId) {
        return repository.findById(partialId).map(mapper::toPartialDto);
    }

    @Override
    public PartialDto createPartial(PartialDto partialDto) {
        Partial partial = mapper.toPartial(partialDto);

        return mapper.toPartialDto(repository.save(partial));
    }
}
