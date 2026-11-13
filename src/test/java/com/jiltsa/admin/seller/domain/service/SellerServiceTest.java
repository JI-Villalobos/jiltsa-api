package com.jiltsa.admin.seller.domain.service;

import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.mapper.SellerMapper;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerServiceTest {
    @Mock
    private SellerRepository repository;
    private SellerService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new SellerService(repository, Mappers.getMapper(SellerMapper.class));
    }

    @Test
    void allSellersOnlyListsActiveOnes() {
        when(repository.findByIsActiveTrue()).thenReturn(List.of(new Seller("Diana Path", 1, true)));

        List<SellerDto> sellers = serviceUnderTest.getAllSellers();

        assertThat(sellers).extracting(SellerDto::getFullName).containsExactly("Diana Path");
    }

    @Test
    void createSellerMapsTheDtoOntoTheEntity() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        serviceUnderTest.createSeller(new SellerDto(null, "Diana Path", 3, true, "MATUTINO"));

        ArgumentCaptor<Seller> saved = ArgumentCaptor.forClass(Seller.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getFullName()).isEqualTo("Diana Path");
        assertThat(saved.getValue().getBranchId()).isEqualTo(3);
        assertThat(saved.getValue().getIsActive()).isTrue();
        assertThat(saved.getValue().getUserTurn()).isEqualTo("MATUTINO");
    }

    @Test
    void updateSellerCopiesEveryFieldOntoTheEntity() {
        Seller seller = new Seller(7, "Diana Path", 1, true, "MATUTINO", null);
        when(repository.findById(7)).thenReturn(Optional.of(seller));
        when(repository.saveAndFlush(any())).thenAnswer(inv -> inv.getArgument(0));

        SellerDto updated = serviceUnderTest.updateSeller(7, new SellerDto(null, "Diana Path López", 3, false, "VESPERTINO"));

        assertThat(seller.getId()).isEqualTo(7);
        assertThat(seller.getFullName()).isEqualTo("Diana Path López");
        assertThat(seller.getBranchId()).isEqualTo(3);
        assertThat(seller.getIsActive()).isFalse();
        assertThat(seller.getUserTurn()).isEqualTo("VESPERTINO");
        assertThat(updated.getId()).isEqualTo(7);
        assertThat(updated.getUserTurn()).isEqualTo("VESPERTINO");
    }

    @Test
    void updateUnknownSellerIsNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceUnderTest.updateSeller(99, new SellerDto(null, "Diana Path", 1, true, null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Seller not found: 99");
    }

    @Test
    void disableSellerFlagsTheEntityInactive() {
        Seller seller = new Seller("Diana Path", 1, true);
        when(repository.findById(1)).thenReturn(Optional.of(seller));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        SellerDto disabled = serviceUnderTest.disableSeller(1);

        assertThat(seller.getIsActive()).isFalse();
        assertThat(disabled.getIsActive()).isFalse();
    }

    @Test
    void disableUnknownSellerIsNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceUnderTest.disableSeller(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Seller not found: 99");
    }
}
