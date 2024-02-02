package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.domain.repository.BillDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillDRepository billDRepository;
    Page<BillDto> getAllBills(Pageable pageable){
        return billDRepository.getBills(pageable);
    }

    Page<BillDto> getPendingBills(Pageable pageable){
        return billDRepository.getPendingBills(pageable);
    }

    BillDto getBill(Integer id){
        return  billDRepository.getBill(id);
    }

    BillDto createBill(BillDto billDto){
        return billDRepository.createBill(billDto);
    }

    List<BillDto> updateBills(List<BillDto> billDtoList){
        return billDRepository.updateBills(billDtoList);
    }
}
