package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.persistence.entity.Product;
import com.jiltsa.admin.sales.persistence.repository.ProductRepository;
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
class ProductControllerTest {
    // each test uses its own branches so results are independent of test order
    private static final String URL = "/jiltsa/api/v1/products/batch";

    @Autowired private MockMvc mockMvc;
    @Autowired private ProductRepository productRepository;

    @Test
    @WithMockUser
    void storesTheWholeBatch() throws Exception {
        int branchId = 801;
        int lines = 1200; // more than one JDBC batch
        String body = IntStream.range(0, lines).mapToObj(i -> productJson(branchId, "K" + i))
                .collect(Collectors.joining(",", "[", "]"));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saved").value(lines));

        List<Product> stored = productRepository.findByBranchIdAndKeyIn(branchId, List.of("K0"));
        assertThat(stored).singleElement().satisfies(product -> {
            assertThat(product.getCategory()).isEqualTo("MEDICAMENTO");
            assertThat(product.getDescription()).isEqualTo("Paracetamol 500mg");
        });
    }

    @Test
    @WithMockUser
    void sameKeyInDifferentBranchesIsAllowed() throws Exception {
        String body = "[" + productJson(811, "SHARED") + "," + productJson(812, "SHARED") + "]";
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());

        // and a later batch may add the key to yet another branch
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content("[" + productJson(813, "SHARED") + "]"))
                .andExpect(status().isCreated());

        assertThat(productRepository.findByBranchIdAndKeyIn(811, List.of("SHARED"))).hasSize(1);
        assertThat(productRepository.findByBranchIdAndKeyIn(812, List.of("SHARED"))).hasSize(1);
        assertThat(productRepository.findByBranchIdAndKeyIn(813, List.of("SHARED"))).hasSize(1);
    }

    @Test
    @WithMockUser
    void keyRepeatedWithinABranchInTheBatchRejectsIt() throws Exception {
        int branchId = 821;
        String body = "[" + productJson(branchId, "A") + "," + productJson(branchId, "B") + "," + productJson(branchId, "A") + "]";

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("branch 821 key A")));

        assertThat(productRepository.findByBranchIdAndKeyIn(branchId, List.of("A", "B"))).isEmpty();
    }

    @Test
    @WithMockUser
    void keyAlreadyStoredForTheBranchRejectsTheBatch() throws Exception {
        int branchId = 831;
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content("[" + productJson(branchId, "OLD") + "]"))
                .andExpect(status().isCreated());

        String body = "[" + productJson(branchId, "NEW") + "," + productJson(branchId, "OLD") + "]";
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("branch 831 key OLD")));

        assertThat(productRepository.findByBranchIdAndKeyIn(branchId, List.of("NEW"))).isEmpty();
    }

    @Test
    void databaseEnforcesOneKeyPerBranch() {
        int branchId = 841;
        productRepository.insertAll(List.of(new Product(null, "X", null, null, branchId)));

        assertThatThrownBy(() -> productRepository.insertAll(List.of(new Product(null, "X", null, null, branchId))))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @WithMockUser
    void invalidLinesAreRejected() throws Exception {
        String missingBranch = "[{\"key\":\"K\",\"category\":\"MEDICAMENTO\"}]";
        String blankKey = "[" + productJson(851, " ") + "]";

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(missingBranch))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(blankKey))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content("[]"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void requiresAuthentication() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content("[" + productJson(861, "K") + "]"))
                .andExpect(status().isUnauthorized());
    }

    private static String productJson(int branchId, String key) {
        return """
                {"branchId":%d,"key":"%s","category":"MEDICAMENTO","description":"Paracetamol 500mg"}
                """.formatted(branchId, key);
    }
}
