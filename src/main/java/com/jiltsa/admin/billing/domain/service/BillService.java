package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import com.jiltsa.admin.billing.persistence.mapper.BillMapper;
import com.jiltsa.admin.billing.persistence.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BillService {
    private final BillRepository repository;
    private final BillMapper mapper;

    public Page<BillDto> getAllBills(Pageable pageable) {
        return mapper.toBillDtoPage(repository.findAll(pageable));
    }

    public Page<BillDto> getPendingBills(Pageable pageable) {
        return mapper.toBillDtoPage(repository.findByIsPaidFalse(pageable));
    }

    public Page<BillDto> getBillsAfterADate(Pageable pageable) {
        return mapper.toBillDtoPage(repository.findByDateAfter(pageable, LocalDateTime.now().minusMonths(4)));
    }

    public Page<BillDto> getBillsBetweenDates(Pageable pageable, LocalDateTime start, LocalDateTime finish) {
        return mapper.toBillDtoPage(repository.findByDateBetween(pageable, start, finish));
    }

    public Optional<BillDto> getBill(Integer id) {
        return repository.findById(id).map(mapper::toBillDto);
    }

    @Transactional
    public BillDto createBill(BillDto billDto) {
        Bill bill = mapper.toBill(billDto);
        bill.setIsActive(true);
        bill.setIsPaid(false);
        if (bill.getLimitPaymentDate() == null)
            bill.setLimitPaymentDate(bill.getDate().plusMonths(1));

        return mapper.toBillDto(repository.save(bill));
    }

    @Transactional
    public BillDto updateBill(BillDto billDto) {
        Bill bill = mapper.toBill(billDto);
        return mapper.toBillDto(repository.save(bill));
    }

    @Transactional
    public List<BillDto> updateBills(List<BillDto> billList) {
        List<Bill> bills = mapper.toBillList(billList);
        return mapper.toBillDtoList(repository.saveAll(bills));
    }

    @Transactional
    public List<BillDto> createBills(List<BillDto> billDtoList) {
        List<Bill> bills = mapper.toBillList(billDtoList);
        bills.forEach(bill -> {
            bill.setIsActive(true);
            bill.setIsPaid(false);
            bill.setLimitPaymentDate(bill.getDate().plusMonths(1));
        });
        return mapper.toBillDtoList(repository.saveAll(bills));
    }
}
