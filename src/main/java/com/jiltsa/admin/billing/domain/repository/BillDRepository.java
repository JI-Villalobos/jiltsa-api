package com.jiltsa.admin.billing.domain.repository;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BillDRepository {
    Page<BillDto> getBills(int page, int elements);
    Page<BillDto> getPendingBills(int page, int elements, String sortBy, String sortDirection);
    Page<BillDto> getBillsAfter(int page, int elements, String sortBy, String sortDirection);
    Page<BillDto> getBillsBetween(int page, int elements, String sortBy, String sortDirection, LocalDateTime start, LocalDateTime finish);
    Optional<BillDto> getBill(Integer id);
    BillDto createBill(BillDto billDto);
    BillDto updateBill(BillDto billDto);
    List<BillDto> createBills(List<BillDto> billDtoList);
    List<BillDto> updateBills(List<BillDto> billList);
}
