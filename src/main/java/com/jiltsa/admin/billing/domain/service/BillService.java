package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.domain.repository.BillDRepository;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillDRepository billDRepository;
    public Page<BillDto> getAllBills(int page, int elements){
        return billDRepository.getBills(page, elements);
    }

    public Page<BillDto> getPendingBills(int page, int elements, String sortBy, String sortDirection){
        return billDRepository.getPendingBills(page, elements, sortBy, sortDirection);
    }

    public Page<BillDto> getBillsAfterADate(int page, int elements, String sortBy, String sortDirection){
        return billDRepository.getBillsAfter(page, elements, sortBy, sortDirection);
    }

    public Page<BillDto> getBillsBetweenDates(int page, int elements, String sortBy, String sortDirection, LocalDateTime start, LocalDateTime finish){
        return billDRepository.getBillsBetween(page, elements, sortBy, sortDirection, start, finish);
    }

    public Optional<BillDto> getBill(Integer id){
        return  billDRepository.getBill(id);
    }

    public BillDto createBill(BillDto billDto){
        return billDRepository.createBill(billDto);
    }

    public BillDto updateBill(BillDto billDto){
        return billDRepository.updateBill(billDto);
    }

    @Transactional
    public List<BillDto> updateBills(List<BillDto> billDtoList){
        return billDRepository.updateBills(billDtoList);
    }

    @Transactional
    public List<BillDto> createBills(List<BillDto> billDtoList){
        return billDRepository.createBills(billDtoList);
    }
}
