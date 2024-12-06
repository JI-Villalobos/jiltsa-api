package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.domain.repository.BillDRepository;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import com.jiltsa.admin.billing.persistence.mapper.BillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BillRepositoryImplementation implements BillDRepository {
    private final BillRepository repository;
    private final BillMapper mapper;


    @Override
    public Page<BillDto> getBills(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return mapper.toBillDtoPage(repository.findAll(pageRequest));
    }

    @Override
    public Page<BillDto> getPendingBills(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return mapper.toBillDtoPage(repository.findByIsPaidFalse(pageRequest));
    }

    @Override
    public Page<BillDto> getBillsAfter(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return mapper.toBillDtoPage(repository.findByDateAfter(pageRequest, LocalDateTime.now().minusMonths(4)));
    }

    @Override
    public Page<BillDto> getBillsBetween(int page, int elements, String sortBy, String sortDirection, LocalDateTime start, LocalDateTime finish) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return mapper.toBillDtoPage(repository.findByDateBetween(pageRequest, start, finish));
    }

    @Override
    public Optional<BillDto> getBill(Integer id) {
        return repository.findById(id).map(mapper::toBillDto);
    }

    @Override
    public BillDto createBill(BillDto billDto) {
        Bill bill = mapper.toBill(billDto);
        bill.setIsActive(true);
        bill.setIsPaid(false);
        bill.setLimitPaymentDate(bill.getDate().plusMonths(1));

        return mapper.toBillDto(repository.save(bill));
    }

    @Override
    public BillDto updateBill(BillDto billDto) {
        Bill bill = mapper.toBill(billDto);
        return mapper.toBillDto(repository.save(bill));
    }

    @Override
    public List<BillDto> createBills(List<BillDto> billDtoList) {
        List<Bill> bills = mapper.toBillList(billDtoList);
        bills.forEach(bill -> {
            bill.setIsActive(true);
            bill.setIsPaid(false);
            bill.setLimitPaymentDate(bill.getDate().plusMonths(1));
        });
        return mapper.toBillDtoList(repository.saveAll(bills));
    }

    @Override
    public List<BillDto> updateBills(List<BillDto> billList) {
        List<Bill> bills = mapper.toBillList(billList);
        return mapper.toBillDtoList(repository.saveAll(bills));
    }
}
