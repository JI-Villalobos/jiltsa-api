package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.domain.dto.DailySaleTotal;
import com.jiltsa.admin.sales.domain.dto.HourlySaleTotal;
import com.jiltsa.admin.sales.persistence.entity.Sale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class SaleRepositoryTest {
    @Autowired
    SaleRepository repositoryUnderTest;

    @BeforeAll
    void setUp() {
        repositoryUnderTest.saveAll(List.of(
                sale(701, "A", 1L, 100.0, 30.0, LocalDateTime.of(2020, 1, 1, 9, 0)),
                sale(701, "B", 2L, 50.0, 10.0, LocalDateTime.of(2020, 1, 1, 21, 59)),
                sale(701, "C", 6L, 25.0, 1.0, LocalDateTime.of(2020, 1, 1, 9, 45)),
                sale(701, "TA", 3L, 500.0, 5.0, LocalDateTime.of(2020, 1, 1, 12, 0)),
                sale(701, "A", 4L, 20.0, 2.0, LocalDateTime.of(2020, 1, 3, 8, 0)),
                sale(702, "A", 1L, 70.0, 7.0, LocalDateTime.of(2020, 1, 1, 10, 0)),
                sale(701, "A", 5L, 999.0, 99.0, LocalDateTime.of(2020, 1, 4, 0, 0))));
    }

    @Test
    void sumDailyByBranchGroupsByDayAndSkipsTa() {
        List<DailySaleTotal> totals = repositoryUnderTest.sumDailyByBranch(
                LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 4, 0, 0));

        assertThat(totals).containsExactlyInAnyOrder(
                new DailySaleTotal(701, LocalDate.of(2020, 1, 1), 175.0, 41.0),
                new DailySaleTotal(701, LocalDate.of(2020, 1, 3), 20.0, 2.0),
                new DailySaleTotal(702, LocalDate.of(2020, 1, 1), 70.0, 7.0));
    }

    @Test
    void sumHourlyByBranchGroupsByDayAndHourAndSkipsTa() {
        List<HourlySaleTotal> totals = repositoryUnderTest.sumHourlyByBranch(
                LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 4, 0, 0));

        assertThat(totals).containsExactlyInAnyOrder(
                new HourlySaleTotal(701, LocalDate.of(2020, 1, 1), 9, 125.0),
                new HourlySaleTotal(701, LocalDate.of(2020, 1, 1), 21, 50.0),
                new HourlySaleTotal(701, LocalDate.of(2020, 1, 3), 8, 20.0),
                new HourlySaleTotal(702, LocalDate.of(2020, 1, 1), 10, 70.0));
    }

    private static Sale sale(Integer branchId, String key, Long ticket, Double total, Double utility, LocalDateTime timestamp) {
        return new Sale(null, branchId, key, null, ticket, "MEDICAMENTO", 1, total, 0.0, utility, total, timestamp, "cajero");
    }
}
