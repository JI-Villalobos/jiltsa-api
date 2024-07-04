package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.domain.repository.PartialDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartialService {
    private final PartialDRepository partialDRepository;

    public Optional<PartialDto> getPartial(Integer partialId){
        return partialDRepository.getPartial(partialId);
    }

    public PartialDto createPartial(PartialDto partialDto){
        return partialDRepository.createPartial(partialDto);
    }
}
