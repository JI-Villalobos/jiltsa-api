package com.jiltsa.admin.operativity.domain.service;

import com.jiltsa.admin.operativity.domain.dto.BreakEvenPointDto;
import com.jiltsa.admin.operativity.persistence.entity.BreakEvenPoint;
import com.jiltsa.admin.operativity.persistence.mapper.BreakEventPointMapper;
import com.jiltsa.admin.operativity.persistence.repository.BreakEventPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BreakEventPointService {
    private final BreakEventPointRepository breakEventPointRepository;
    private final BreakEventPointMapper mapper;

    public BreakEvenPointDto saveBEP(BreakEvenPointDto breakEvenPointDto){
        BreakEvenPoint breakEvenPoint = mapper.toBreakEvenPoint(breakEvenPointDto);

        return mapper.toBreakEvenPointDto(breakEventPointRepository.save(breakEvenPoint));
    }

    public Optional<BreakEvenPointDto> getBreakEvenPointDto(Integer branchId){
        return breakEventPointRepository.findByBranchId(branchId).map(mapper::toBreakEvenPointDto);
    }
}
