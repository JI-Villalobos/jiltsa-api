package com.jiltsa.admin.promotions.controller;

import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import com.jiltsa.admin.promotions.persistence.repository.PromotionItemRepository;
import com.jiltsa.admin.promotions.persistence.repository.PromotionRepository;
import com.jiltsa.admin.promotions.persistence.repository.RecordingLossExpirationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Promotions are dated in 2019, one month per test, so range queries do not see each other's rows. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class PromotionControllerTest {
    private static final String URL = "/jiltsa/api/v1/promotions";

    @Autowired private MockMvc mockMvc;
    @Autowired private PromotionRepository repository;
    @Autowired private PromotionItemRepository itemRepository;
    @Autowired private RecordingLossExpirationRepository lossRepository;

    @Test
    @WithMockUser
    void createsAPromotionActiveAndDatedToday() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"promotionName\":\"CREADA HOY\",\"branchId\":1,\"promotionEndDate\":\"2099-12-31\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.promotionName").value("CREADA HOY"))
                .andExpect(jsonPath("$.creationDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    @WithMockUser
    void rejectsUnknownBranchAndEndBeforeCreation() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"promotionName\":\"X\",\"branchId\":999999,\"promotionEndDate\":\"2099-12-31\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Branch not found: 999999"));
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"promotionName\":\"X\",\"branchId\":1,\"creationDate\":\"2019-01-10\",\"promotionEndDate\":\"2019-01-01\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("before creationDate")));
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"promotionName\":\"\",\"branchId\":1,\"promotionEndDate\":\"2099-12-31\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void getsAPromotionWithItsItems() throws Exception {
        Promotion promotion = repository.save(new Promotion("CON ITEMS", 1, LocalDate.of(2019, 2, 1), LocalDate.of(2019, 2, 28), true));
        itemRepository.save(new PromotionItem("K1", "Paracetamol", 3, 50.0, 30.0, 20.0, promotion.getId(), LocalDate.of(2019, 6, 1)));

        mockMvc.perform(get(URL + "/" + promotion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionName").value("CON ITEMS"))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].key").value("K1"))
                .andExpect(jsonPath("$.items[0].promotionId").value(promotion.getId()));
        mockMvc.perform(get(URL + "/999999")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void listsABranchsPromotionsWithinARangeAndItsActiveOnes() throws Exception {
        repository.save(new Promotion("MARZO 1", 2, LocalDate.of(2019, 3, 5), LocalDate.of(2019, 3, 31), true));
        repository.save(new Promotion("MARZO 2", 2, LocalDate.of(2019, 3, 20), LocalDate.of(2019, 3, 31), false));
        repository.save(new Promotion("MARZO OTRA SUCURSAL", 3, LocalDate.of(2019, 3, 10), LocalDate.of(2019, 3, 31), true));
        repository.save(new Promotion("FUERA DE RANGO", 2, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30), true));

        mockMvc.perform(get(URL + "/by-branch/2").param("initialDate", "2019-03-01").param("finalDate", "2019-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].promotionName").value("MARZO 2"))
                .andExpect(jsonPath("$[1].promotionName").value("MARZO 1"));

        mockMvc.perform(get(URL + "/by-branch/2/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.promotionName=='MARZO 1')]").exists())
                .andExpect(jsonPath("$[?(@.promotionName=='MARZO 2')]").doesNotExist());
    }

    @Test
    @WithMockUser
    void updatesAPromotion() throws Exception {
        Promotion promotion = repository.save(new Promotion("ANTES", 1, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 31), true));

        mockMvc.perform(put(URL + "/" + promotion.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"promotionName\":\"DESPUES\",\"branchId\":2,\"promotionEndDate\":\"2019-06-15\",\"active\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionName").value("DESPUES"))
                .andExpect(jsonPath("$.branchId").value(2))
                .andExpect(jsonPath("$.creationDate").value("2019-05-01"))
                .andExpect(jsonPath("$.promotionEndDate").value("2019-06-15"))
                .andExpect(jsonPath("$.active").value(false));
        mockMvc.perform(put(URL + "/999999").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"promotionName\":\"X\",\"branchId\":1,\"promotionEndDate\":\"2019-06-15\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void closingRecordsTheLossAtPurchasePriceOnce() throws Exception {
        Promotion promotion = repository.save(new Promotion("CERRAR", 3, LocalDate.of(2019, 7, 1), LocalDate.of(2019, 7, 31), true));
        itemRepository.save(new PromotionItem("A", "Ibuprofeno", 4, 100.0, 60.0, 25.5, promotion.getId(), LocalDate.of(2019, 10, 1)));
        itemRepository.save(new PromotionItem("B", "Omeprazol", 2, 80.0, 40.0, 10.0, promotion.getId(), LocalDate.of(2019, 11, 1)));

        mockMvc.perform(post(URL + "/" + promotion.getId() + "/close"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.promotionId").value(promotion.getId()))
                .andExpect(jsonPath("$.branchId").value(3))
                .andExpect(jsonPath("$.totalLoss").value(4 * 25.5 + 2 * 10.0))
                .andExpect(jsonPath("$.creationDate").value(LocalDate.now().toString()));
        assertThat(repository.findById(promotion.getId())).get().extracting(Promotion::getActive).isEqualTo(false);
        assertThat(lossRepository.findByPromotionId(promotion.getId())).isPresent();

        mockMvc.perform(post(URL + "/" + promotion.getId() + "/close"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("already closed")));
        mockMvc.perform(post(URL + "/999999/close")).andExpect(status().isNotFound());

        // closed for good: it cannot be reactivated nor deleted
        mockMvc.perform(put(URL + "/" + promotion.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"promotionName\":\"CERRAR\",\"branchId\":3,\"promotionEndDate\":\"2019-07-31\",\"active\":true}"))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deletesAPromotionWithItsItemsUnlessItsLossIsRecorded() throws Exception {
        Promotion open = repository.save(new Promotion("BORRAR", 1, LocalDate.of(2019, 8, 1), LocalDate.of(2019, 8, 31), true));
        PromotionItem item = itemRepository.save(new PromotionItem("C", "Loratadina", 1, 10.0, 5.0, 2.0, open.getId(), LocalDate.of(2019, 12, 1)));

        mockMvc.perform(delete(URL + "/" + open.getId())).andExpect(status().isNoContent());
        assertThat(repository.findById(open.getId())).isEmpty();
        assertThat(itemRepository.findById(item.getId())).isEmpty();
        mockMvc.perform(delete(URL + "/" + open.getId())).andExpect(status().isNotFound());

        Promotion closed = repository.save(new Promotion("CERRADA", 1, LocalDate.of(2019, 8, 2), LocalDate.of(2019, 8, 31), true));
        mockMvc.perform(post(URL + "/" + closed.getId() + "/close")).andExpect(status().isCreated());
        mockMvc.perform(delete(URL + "/" + closed.getId()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("cannot be deleted")));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void onlyAdminDeletes() throws Exception {
        mockMvc.perform(delete(URL + "/999999")).andExpect(status().isForbidden());
    }
}
