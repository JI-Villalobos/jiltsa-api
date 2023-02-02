package com.jiltsa.admin.seller.domain.service;

import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.domain.repository.SellerDRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerServiceTest {
    @Mock
    private SellerDRepository repository;
    @InjectMocks
    private SellerDService serviceUnderTest;
    @BeforeEach
    void setUp(){
        serviceUnderTest = new SellerDService(repository);
    }
    @Test
    void shouldGetAllSellers() {
        //when
        serviceUnderTest.getAllSellers();

        //then
        verify(repository).getAllSellers();
    }

    @Test
    void shouldCreateANewSeller() {
        //given
        SellerDto sellerDto = new SellerDto(1, "Diana Path", 1,true);

        //when
        serviceUnderTest.newSeller(sellerDto);

        //then
        ArgumentCaptor<SellerDto> sellerDtoArgumentCaptor = ArgumentCaptor.forClass(SellerDto.class);
        verify(repository).newSeller(sellerDtoArgumentCaptor.capture());

        SellerDto captureSellerDto = sellerDtoArgumentCaptor.getValue();

        assertThat(captureSellerDto).isEqualTo(sellerDto);
    }

    @Test
    void shouldDisableSeller() {
        //given
        SellerDto sellerDto = new SellerDto(1, "Diana Path", 1,true);
        serviceUnderTest.newSeller(sellerDto);

        //when
        serviceUnderTest.disableSeller(1);

        //then
        verify(repository).disableSeller(1);
    }

    @Test
    void shouldGetSellerById() {
        //when
        serviceUnderTest.getSeller(1);

        //then
        verify(repository).getSeller(1);
    }
}