package com.jiltsa.admin.billing.domain.repository;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillDRepository {
    Page<BillDto> getBills(Pageable pageable);
    Page<BillDto> getPendingBills(Pageable pageable);
    BillDto createBill(BillDto billDto);
    List<BillDto> updateBills(List<BillDto> billList);
}
