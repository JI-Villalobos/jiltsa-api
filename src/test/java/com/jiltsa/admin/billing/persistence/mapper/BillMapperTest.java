package com.jiltsa.admin.billing.persistence.mapper;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class BillMapperTest {

    @Test
    void shouldMapToBillDto() {
        //given
        BillMapper mapper = Mappers.getMapper(BillMapper.class);
        Bill bill = new Bill(1, LocalDateTime.now(), UUID.randomUUID().toString(), "NAZAS", 1, 245.00, LocalDateTime.now().plusMonths(1),  false, null, 1, true);

        //when
        BillDto billDto = mapper.toBillDto(bill);

        //then
        assertThat(billDto).isNotNull();
        assertThat(billDto).isInstanceOf(BillDto.class);
    }

    @Test
    @Disabled
    void shouldMapToBillDtoList() {
        //given
        BillMapper mapper = Mappers.getMapper(BillMapper.class);
        Pageable pageReq = PageRequest.of(1, 2);

        Bill bill = new Bill(1, LocalDateTime.now(), UUID.randomUUID().toString(), "NAZAS", 1, 245.00, LocalDateTime.now().plusMonths(1),  false, null, 1, true);
        Bill bill2 = new Bill(1, LocalDateTime.now(), UUID.randomUUID().toString(), "NAZAS", 1, 245.00, LocalDateTime.now().plusMonths(1),  false, null, 1, true);


        //when
        //List<BillDto> billDtoList = mapper.toBillDtoList(bills);

        //then
        //assertThat(billDtoList).isNotNull();
        //assertThat(billDtoList.size()).isEqualTo(2);
    }

    @Test
    void shouldMapToBill() {
        //given
        BillMapper mapper = Mappers.getMapper(BillMapper.class);
        LocalDateTime dateTime = LocalDateTime.now();
        BillDto billDto = new BillDto(dateTime, UUID.randomUUID().toString(), "NAZAS",
                1, 245.00, 1);

        //when
        Bill bill = mapper.toBill(billDto);

        //then
        assertThat(bill).isInstanceOf(Bill.class);
        assertThat(bill.getIsActive()).isNull();
        assertThat(bill.getLimitPaymentDate()).isNull();
        assertThat(bill.getIsPaid()).isNull();
    }
}