package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.domain.repository.PartialDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartialService {
    private final PartialDRepository partialDRepository;

    public Optional<PartialDto> getPartial(Integer partialId){
        return partialDRepository.getPartial(partialId);
    }

    @Transactional
    public PartialDto createPartial(PartialDto partialDto){
        return partialDRepository.createPartial(partialDto);
    }
}
