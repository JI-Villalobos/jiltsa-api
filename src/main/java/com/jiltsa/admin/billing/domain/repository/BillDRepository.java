package com.jiltsa.admin.billing.domain.repository;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BillDRepository {
    Page<BillDto> getBills(int page, int elements);
    Page<BillDto> getPendingBills(int page, int elements);
    Optional<BillDto> getBill(Integer id);
    BillDto createBill(BillDto billDto);
    List<BillDto> updateBills(List<BillDto> billList);
}
