package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import com.jiltsa.admin.billing.persistence.mapper.BillMapper;
import com.jiltsa.admin.billing.persistence.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<BillDto> getAllBills(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return mapper.toBillDtoPage(repository.findAll(pageRequest));
    }

    public Page<BillDto> getPendingBills(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return mapper.toBillDtoPage(repository.findByIsPaidFalse(pageRequest));
    }

    public Page<BillDto> getBillsAfterADate(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return mapper.toBillDtoPage(repository.findByDateAfter(pageRequest, LocalDateTime.now().minusMonths(4)));
    }

    public Page<BillDto> getBillsBetweenDates(int page, int elements, String sortBy, String sortDirection, LocalDateTime start, LocalDateTime finish) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return mapper.toBillDtoPage(repository.findByDateBetween(pageRequest, start, finish));
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
