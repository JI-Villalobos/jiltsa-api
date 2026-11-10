package com.jiltsa.admin.promotions.controller;

import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import com.jiltsa.admin.promotions.persistence.entity.RecordingLossExpiration;
import com.jiltsa.admin.promotions.persistence.repository.PromotionItemRepository;
import com.jiltsa.admin.promotions.persistence.repository.PromotionRepository;
import com.jiltsa.admin.promotions.persistence.repository.RecordingLossExpirationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Records are dated in 2017, one month per test, so range queries do not see each other's rows. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class RecordingLossExpirationControllerTest {
    private static final String URL = "/jiltsa/api/v1/expiration-losses";

    @Autowired private MockMvc mockMvc;
    @Autowired private PromotionRepository promotionRepository;
    @Autowired private PromotionItemRepository itemRepository;
    @Autowired private RecordingLossExpirationRepository repository;

    private Promotion closedPromotion(String name, int branchId, LocalDate end) {
        return promotionRepository.save(new Promotion(name, branchId, end.withDayOfMonth(1), end, false));
    }

    @Test
    @WithMockUser
    void getsARecordByIdAndByPromotion() throws Exception {
        Promotion promotion = closedPromotion("REGISTRO", 1, LocalDate.of(2017, 1, 31));
        RecordingLossExpiration record = repository.save(new RecordingLossExpiration(promotion.getId(), 1, 123.45, LocalDate.of(2017, 1, 31)));

        mockMvc.perform(get(URL + "/" + record.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionId").value(promotion.getId()))
                .andExpect(jsonPath("$.totalLoss").value(123.45));
        mockMvc.perform(get(URL + "/by-promotion/" + promotion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(record.getId()));
        mockMvc.perform(get(URL + "/999999")).andExpect(status().isNotFound());
        mockMvc.perform(get(URL + "/by-promotion/999999")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void listsABranchsRecordsWithinARange() throws Exception {
        Promotion first = closedPromotion("FEB 1", 2, LocalDate.of(2017, 2, 10));
        Promotion second = closedPromotion("FEB 2", 2, LocalDate.of(2017, 2, 20));
        Promotion otherBranch = closedPromotion("FEB OTRA", 3, LocalDate.of(2017, 2, 15));
        Promotion outOfRange = closedPromotion("MAR", 2, LocalDate.of(2017, 3, 1));
        repository.save(new RecordingLossExpiration(first.getId(), 2, 10.0, LocalDate.of(2017, 2, 10)));
        repository.save(new RecordingLossExpiration(second.getId(), 2, 20.0, LocalDate.of(2017, 2, 20)));
        repository.save(new RecordingLossExpiration(otherBranch.getId(), 3, 30.0, LocalDate.of(2017, 2, 15)));
        repository.save(new RecordingLossExpiration(outOfRange.getId(), 2, 40.0, LocalDate.of(2017, 3, 1)));

        mockMvc.perform(get(URL + "/by-branch/2").param("initialDate", "2017-02-01").param("finalDate", "2017-02-28"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].promotionId").value(second.getId()))
                .andExpect(jsonPath("$[1].promotionId").value(first.getId()));
    }

    @Test
    @WithMockUser
    void projectsTheRangeTotalAndBreaksItDownByPromotionAndMedicine() throws Exception {
        Promotion a = closedPromotion("PROY A", 1, LocalDate.of(2017, 4, 10));
        itemRepository.save(new PromotionItem("A1", "Amoxicilina", 3, 90.0, 60.0, 30.0, a.getId(), LocalDate.of(2017, 8, 1)));
        itemRepository.save(new PromotionItem("A2", "Metformina", 10, 50.0, 35.0, 12.5, a.getId(), LocalDate.of(2017, 9, 1)));
        Promotion b = closedPromotion("PROY B", 1, LocalDate.of(2017, 4, 20));
        itemRepository.save(new PromotionItem("B1", "Losartan", 1, 70.0, 50.0, 44.0, b.getId(), LocalDate.of(2017, 10, 1)));
        Promotion other = closedPromotion("PROY OTRA SUCURSAL", 3, LocalDate.of(2017, 4, 15));
        repository.save(new RecordingLossExpiration(a.getId(), 1, 215.0, LocalDate.of(2017, 4, 10)));
        repository.save(new RecordingLossExpiration(b.getId(), 1, 44.0, LocalDate.of(2017, 4, 20)));
        repository.save(new RecordingLossExpiration(other.getId(), 3, 100.0, LocalDate.of(2017, 4, 15)));

        mockMvc.perform(get(URL + "/projection").param("branchId", "1").param("initialDate", "2017-04-01").param("finalDate", "2017-04-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.branchId").value(1))
                .andExpect(jsonPath("$.totalLoss").value(259.0))
                .andExpect(jsonPath("$.promotions").value(2))
                .andExpect(jsonPath("$.promotionLosses[0].promotionName").value("PROY B"))
                .andExpect(jsonPath("$.promotionLosses[0].totalLoss").value(44.0))
                .andExpect(jsonPath("$.promotionLosses[1].promotionName").value("PROY A"))
                .andExpect(jsonPath("$.promotionLosses[1].promotionEndDate").value("2017-04-10"))
                .andExpect(jsonPath("$.promotionLosses[1].recordedOn").value("2017-04-10"))
                .andExpect(jsonPath("$.promotionLosses[1].items.length()").value(2))
                // biggest loss first: 10 x 12.5 = 125 before 3 x 30 = 90
                .andExpect(jsonPath("$.promotionLosses[1].items[0].key").value("A2"))
                .andExpect(jsonPath("$.promotionLosses[1].items[0].loss").value(125.0))
                .andExpect(jsonPath("$.promotionLosses[1].items[1].description").value("Amoxicilina"))
                .andExpect(jsonPath("$.promotionLosses[1].items[1].loss").value(90.0));

        // every branch
        mockMvc.perform(get(URL + "/projection").param("initialDate", "2017-04-01").param("finalDate", "2017-04-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.branchId").doesNotExist())
                .andExpect(jsonPath("$.totalLoss").value(359.0))
                .andExpect(jsonPath("$.promotions").value(3));

        // empty range
        mockMvc.perform(get(URL + "/projection").param("branchId", "1").param("initialDate", "2017-05-01").param("finalDate", "2017-05-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalLoss").value(0.0))
                .andExpect(jsonPath("$.promotionLosses").isEmpty());
    }
}
