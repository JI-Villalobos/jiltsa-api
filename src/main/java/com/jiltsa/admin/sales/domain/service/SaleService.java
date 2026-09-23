package com.jiltsa.admin.sales.domain.service;

import com.jiltsa.admin.common.exception.ConflictException;
import com.jiltsa.admin.sales.domain.dto.CreateSaleDto;
import com.jiltsa.admin.sales.domain.dto.SaleBatchResultDto;
import com.jiltsa.admin.sales.persistence.mapper.SaleMapper;
import com.jiltsa.admin.sales.persistence.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository repository;
    private final SaleMapper mapper;

    /**
     * All or nothing. A ticket lists each product once, so a batch is rejected if it
     * repeats a (branch, ticket, key) line or contains one already stored: that is a
     * re-upload. The unique index on those columns backs this up against concurrent uploads.
     */
    @Transactional
    public SaleBatchResultDto createSales(List<CreateSaleDto> sales) {
        Map<Integer, Set<Line>> linesByBranch = new TreeMap<>();
        List<String> repeated = new ArrayList<>();
        for (CreateSaleDto sale : sales) {
            Line line = new Line(sale.ticket(), sale.key());
            if (!linesByBranch.computeIfAbsent(sale.branchId(), branch -> new LinkedHashSet<>()).add(line)) {
                repeated.add(describe(sale.branchId(), line));
            }
        }
        if (!repeated.isEmpty()) {
            throw new ConflictException("Sale lines repeated within the batch: " + String.join(", ", repeated));
        }

        List<String> existing = new ArrayList<>();
        linesByBranch.forEach((branchId, lines) -> {
            Set<Long> tickets = new HashSet<>();
            lines.forEach(line -> tickets.add(line.ticket()));
            repository.findByBranchIdAndTicketIn(branchId, tickets).stream()
                    .map(sale -> new Line(sale.getTicket(), sale.getKey()))
                    .filter(lines::contains)
                    .forEach(line -> existing.add(describe(branchId, line)));
        });
        if (!existing.isEmpty()) {
            throw new ConflictException("Sales already uploaded: " + String.join(", ", existing));
        }

        repository.insertAll(mapper.toSaleList(sales));
        return new SaleBatchResultDto(sales.size());
    }

    private record Line(Long ticket, String key) {
    }

    private static String describe(Integer branchId, Line line) {
        return "branch " + branchId + " ticket " + line.ticket() + " key " + line.key();
    }
}
