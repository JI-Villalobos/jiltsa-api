package com.jiltsa.admin.promotions.controller;

import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import com.jiltsa.admin.promotions.persistence.repository.PromotionItemRepository;
import com.jiltsa.admin.promotions.persistence.repository.PromotionRepository;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class PromotionItemControllerTest {
    private static final String URL = "/jiltsa/api/v1/promotion-items";

    @Autowired private MockMvc mockMvc;
    @Autowired private PromotionRepository promotionRepository;
    @Autowired private PromotionItemRepository repository;

    private Promotion promotion(String name, boolean active) {
        return promotionRepository.save(new Promotion(name, 1, LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 31), active));
    }

    private static String json(String key, int quantity, Integer promotionId) {
        return "{\"key\":\"" + key + "\",\"description\":\"Desc " + key + "\",\"quantity\":" + quantity
                + ",\"normalPrice\":100.0,\"promotionPrice\":70.0,\"purchasePrice\":40.0,\"promotionId\":" + promotionId
                + ",\"expirationDate\":\"2018-06-30\"}";
    }

    @Test
    @WithMockUser
    void addsOneItemToAnActivePromotion() throws Exception {
        Promotion promotion = promotion("ITEM UNO", true);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json("UNO", 5, promotion.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.key").value("UNO"))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.promotionId").value(promotion.getId()))
                .andExpect(jsonPath("$.expirationDate").value("2018-06-30"));
        assertThat(repository.findByPromotionIdOrderByExpirationDateAsc(promotion.getId())).hasSize(1);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json("UNO", -1, promotion.getId())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void addsABatchOfItemsAllOrNothing() throws Exception {
        Promotion open = promotion("LOTE ABIERTA", true);
        Promotion closed = promotion("LOTE CERRADA", false);

        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON)
                        .content("[" + json("L1", 1, open.getId()) + "," + json("L2", 2, open.getId()) + "]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].key").value("L2"));

        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON)
                        .content("[" + json("L3", 1, open.getId()) + "," + json("L4", 1, closed.getId()) + "]"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("closed")));
        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON)
                        .content("[" + json("L5", 1, open.getId()) + "," + json("L6", 1, 999999) + "]"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Promotion not found: 999999"));
        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON).content("[]"))
                .andExpect(status().isBadRequest());

        assertThat(repository.findByPromotionIdOrderByExpirationDateAsc(open.getId()))
                .extracting(PromotionItem::getKey).containsExactlyInAnyOrder("L1", "L2");
    }

    @Test
    @WithMockUser
    void rejectsItemsForAClosedPromotion() throws Exception {
        Promotion closed = promotion("CERRADA", false);
        PromotionItem item = repository.save(new PromotionItem("H", "Historico", 1, 10.0, 5.0, 2.0, closed.getId(), LocalDate.of(2018, 6, 1)));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json("NUEVO", 1, closed.getId())))
                .andExpect(status().isConflict());
        mockMvc.perform(put(URL + "/" + item.getId()).contentType(MediaType.APPLICATION_JSON).content(json("H", 0, closed.getId())))
                .andExpect(status().isConflict());
        assertThat(repository.findById(item.getId())).get().extracting(PromotionItem::getQuantity).isEqualTo(1);
    }

    @Test
    @WithMockUser
    void listsUpdatesAndGetsItems() throws Exception {
        Promotion promotion = promotion("LISTAR", true);
        PromotionItem late = repository.save(new PromotionItem("TARDE", "Expira tarde", 2, 10.0, 5.0, 2.0, promotion.getId(), LocalDate.of(2018, 9, 1)));
        repository.save(new PromotionItem("PRONTO", "Expira pronto", 2, 10.0, 5.0, 2.0, promotion.getId(), LocalDate.of(2018, 3, 1)));

        mockMvc.perform(get(URL + "/by-promotion/" + promotion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].key").value("PRONTO"))
                .andExpect(jsonPath("$[1].key").value("TARDE"));

        mockMvc.perform(put(URL + "/" + late.getId()).contentType(MediaType.APPLICATION_JSON).content(json("TARDE", 1, promotion.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(late.getId()))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.purchasePrice").value(40.0));
        mockMvc.perform(get(URL + "/" + late.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(1));
        mockMvc.perform(get(URL + "/999999")).andExpect(status().isNotFound());
        mockMvc.perform(put(URL + "/999999").contentType(MediaType.APPLICATION_JSON).content(json("X", 1, promotion.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deletesAnItem() throws Exception {
        Promotion promotion = promotion("BORRAR ITEM", true);
        PromotionItem item = repository.save(new PromotionItem("D", "Borrar", 1, 10.0, 5.0, 2.0, promotion.getId(), LocalDate.of(2018, 6, 1)));

        mockMvc.perform(delete(URL + "/" + item.getId())).andExpect(status().isNoContent());
        assertThat(repository.findById(item.getId())).isEmpty();
        mockMvc.perform(delete(URL + "/" + item.getId())).andExpect(status().isNotFound());
    }
}
