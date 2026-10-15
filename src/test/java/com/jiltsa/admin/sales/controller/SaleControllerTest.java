package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.persistence.entity.Sale;
import com.jiltsa.admin.sales.persistence.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class SaleControllerTest {
    // each test uses its own branch so row counts are independent of test order
    private static final String URL = "/jiltsa/api/v1/sales/batch";

    @Autowired private MockMvc mockMvc;
    @Autowired private SaleRepository saleRepository;

    @Test
    @WithMockUser
    void storesTheWholeBatch() throws Exception {
        int branchId = 901;
        int lines = 1200; // more than one JDBC batch
        String body = IntStream.range(0, lines).mapToObj(i -> saleJson(branchId, "K" + i, 2))
                .collect(Collectors.joining(",", "[", "]"));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saved").value(lines));

        List<Sale> stored = saleRepository.findByBranchId(branchId);
        assertThat(stored).hasSize(lines);
        Sale first = stored.stream().filter(s -> s.getKey().equals("K0")).findFirst().orElseThrow();
        assertThat(first.getTicket()).isEqualTo(77L);
        assertThat(first.getCategory()).isEqualTo("MEDICAMENTO");
        assertThat(first.getQuantity()).isEqualTo(2);
        assertThat(first.getTotal()).isEqualTo(50.0);
        assertThat(first.getApproximatedUtility()).isEqualTo(20.0);
        assertThat(first.getUser()).isEqualTo("cajero");
        assertThat(first.getTimestamp()).isEqualTo(LocalDateTime.parse("2026-09-22T10:15:30"));
    }

    @Test
    @WithMockUser
    void oneInvalidLineRejectsTheBatch() throws Exception {
        int branchId = 902;
        String body = "[" + saleJson(branchId, "OK", 1) + "," + saleJson(branchId, "BAD", 0) + "]";

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());

        assertThat(saleRepository.findByBranchId(branchId)).isEmpty();
    }

    @Test
    @WithMockUser
    void emptyBatchIsRejected() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content("[]"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void oversizedBatchIsRejected() throws Exception {
        int branchId = 903;
        String body = IntStream.rangeClosed(0, SaleController.MAX_BATCH_SIZE).mapToObj(i -> saleJson(branchId, "K" + i, 1))
                .collect(Collectors.joining(",", "[", "]"));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());

        assertThat(saleRepository.findByBranchId(branchId)).isEmpty();
    }

    @Test
    @WithMockUser
    void reUploadIsRejected() throws Exception {
        int branchId = 911;
        String first = "[" + saleJson(branchId, 1L, "A", 1) + "," + saleJson(branchId, 1L, "B", 1) + "]";
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(first))
                .andExpect(status().isCreated());

        // an overlapping batch: one new line, one already stored
        String second = "[" + saleJson(branchId, 2L, "A", 1) + "," + saleJson(branchId, 1L, "B", 1) + "]";
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(second))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("branch 911 ticket 1 key B")));

        assertThat(saleRepository.findByBranchId(branchId)).hasSize(2);
    }

    @Test
    @WithMockUser
    void lineRepeatedWithinTheBatchRejectsIt() throws Exception {
        int branchId = 912;
        String body = "[" + saleJson(branchId, 5L, "A", 1) + "," + saleJson(branchId, 5L, "A", 2) + "]";

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("branch 912 ticket 5 key A")));

        assertThat(saleRepository.findByBranchId(branchId)).isEmpty();
    }

    @Test
    @WithMockUser
    void sameProductOnOtherTicketsOrBranchesIsAllowed() throws Exception {
        String body = "[" + saleJson(913, 1L, "A", 1) + "," + saleJson(913, 2L, "A", 1) + "," + saleJson(914, 1L, "A", 1) + "]";

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());

        assertThat(saleRepository.findByBranchId(913)).hasSize(2);
        assertThat(saleRepository.findByBranchId(914)).hasSize(1);
    }

    @Test
    void databaseEnforcesOneLinePerTicketAndProduct() {
        Sale sale = new Sale(null, 915, "X", null, 1L, "MEDICAMENTO", 1, 1.0, 1.0, 0.0, 1.0, LocalDateTime.now(), "cajero");
        saleRepository.insertAll(List.of(sale));

        assertThatThrownBy(() -> saleRepository.insertAll(List.of(sale)))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void requiresAuthentication() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content("[" + saleJson(904, "K", 1) + "]"))
                .andExpect(status().isUnauthorized());
    }

    private static String saleJson(int branchId, String key, int quantity) {
        return saleJson(branchId, 77L, key, quantity);
    }

    private static String saleJson(int branchId, long ticket, String key, int quantity) {
        return """
                {"branchId":%d,"key":"%s","description":"Paracetamol 500mg","ticket":%d,"category":"MEDICAMENTO",
                 "quantity":%d,"price":25.0,"purchasePrice":15.0,"approximatedUtility":20.0,"total":50.0,
                 "timestamp":"2026-09-22T10:15:30","user":"cajero"}
                """.formatted(branchId, key, ticket, quantity);
    }
}
