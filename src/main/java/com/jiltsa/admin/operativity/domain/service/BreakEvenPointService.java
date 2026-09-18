package com.jiltsa.admin.operativity.domain.service;

import com.jiltsa.admin.operativity.domain.dto.BreakEvenPointDto;
import com.jiltsa.admin.operativity.persistence.entity.BreakEvenPoint;
import com.jiltsa.admin.operativity.persistence.mapper.BreakEvenPointMapper;
import com.jiltsa.admin.operativity.persistence.repository.BreakEvenPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BreakEvenPointService {
    private final BreakEvenPointRepository breakEventPointRepository;
    private final BreakEvenPointMapper mapper;

    @Transactional
    public BreakEvenPointDto saveBEP(BreakEvenPointDto breakEvenPointDto){
        BreakEvenPoint breakEvenPoint = mapper.toBreakEvenPoint(breakEvenPointDto);

        return mapper.toBreakEvenPointDto(breakEventPointRepository.save(breakEvenPoint));
    }

    public Optional<BreakEvenPointDto> getBreakEvenPoint(Integer branchId){
        return breakEventPointRepository.findByBranchId(branchId).map(mapper::toBreakEvenPointDto);
    }
}
