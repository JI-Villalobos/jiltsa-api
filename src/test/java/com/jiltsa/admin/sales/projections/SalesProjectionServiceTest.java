package com.jiltsa.admin.sales.projections;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.service.BranchService;
import com.jiltsa.admin.sales.domain.dto.BranchDailySalesDto;
import com.jiltsa.admin.sales.domain.dto.DailySaleTotal;
import com.jiltsa.admin.sales.domain.dto.DailySalesDto;
import com.jiltsa.admin.sales.domain.dto.DailySalesProjectionDto;
import com.jiltsa.admin.sales.persistence.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesProjectionServiceTest {
    private static final LocalDate TODAY = LocalDate.of(2026, 10, 14);
    private static final LocalDate FIRST_DAY = LocalDate.of(2026, 9, 15);

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private BranchService branchService;
    @InjectMocks
    private SalesProjectionService serviceUnderTest;

    @Test
    void getDailySalesCoversThirtyDaysAndMarksDaysWithoutSalesPending() {
        when(branchService.getAll()).thenReturn(List.of(new BranchDto(1, "nazas", true), new BranchDto(2, "coyote", true)));
        when(saleRepository.sumDailyByBranch(FIRST_DAY.atStartOfDay(), TODAY.plusDays(1).atStartOfDay())).thenReturn(List.of(
                new DailySaleTotal(1, FIRST_DAY, 100.0, 30.0),
                new DailySaleTotal(1, TODAY.minusDays(2), 200.0, null)));

        DailySalesProjectionDto projection = serviceUnderTest.getDailySales(TODAY);

        assertThat(projection.initialDate()).isEqualTo(FIRST_DAY);
        assertThat(projection.finalDate()).isEqualTo(TODAY);

        BranchDailySalesDto nazas = projection.branches().get(0);
        assertThat(nazas.days()).hasSize(30);
        assertThat(nazas.days()).first().isEqualTo(new DailySalesDto(FIRST_DAY, 100.0, 30.0, false));
        assertThat(nazas.days()).last().isEqualTo(new DailySalesDto(TODAY, 0, 0, true));
        assertThat(nazas.days()).filteredOn(day -> !day.pending())
                .containsExactly(new DailySalesDto(FIRST_DAY, 100.0, 30.0, false), new DailySalesDto(TODAY.minusDays(2), 200.0, 0, false));
        assertThat(nazas.totalSales()).isEqualTo(300.0);
        assertThat(nazas.averageDailySales()).isEqualTo(150.0);

        BranchDailySalesDto coyote = projection.branches().get(1);
        assertThat(coyote.days()).allMatch(DailySalesDto::pending);
        assertThat(coyote.totalSales()).isZero();
        assertThat(coyote.averageDailySales()).isZero();
    }
}
