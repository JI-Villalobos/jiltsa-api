package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.persistence.entity.Category;
import com.jiltsa.admin.sales.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class CategoryControllerTest {
    // each test uses its own names so results are independent of test order
    private static final String URL = "/jiltsa/api/v1/categories";

    @Autowired private MockMvc mockMvc;
    @Autowired private CategoryRepository repository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void createsOneCategory() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json("CREADA")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.categoryName").value("CREADA"));

        assertThat(repository.findByCategoryNameIn(List.of("CREADA"))).hasSize(1);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void existingNameIsRejectedIgnoringCase() throws Exception {
        repository.save(new Category(null, "DUPLICADA"));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json("duplicada")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("DUPLICADA")));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void createsTheWholeBatch() throws Exception {
        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON)
                        .content("[" + json("LOTE A") + "," + json("LOTE B") + "]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saved").value(2));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].categoryName", hasItems("LOTE A", "LOTE B")));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void batchWithARepeatedOrExistingNameStoresNothing() throws Exception {
        repository.save(new Category(null, "LOTE EXISTENTE"));

        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON)
                        .content("[" + json("LOTE NUEVO 1") + "," + json("lote nuevo 1") + "]"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("lote nuevo 1")));
        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON)
                        .content("[" + json("LOTE NUEVO 2") + "," + json("LOTE EXISTENTE") + "]"))
                .andExpect(status().isConflict());

        assertThat(repository.findByCategoryNameIn(List.of("LOTE NUEVO 1", "LOTE NUEVO 2"))).isEmpty();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void updatesTheName() throws Exception {
        Category category = repository.save(new Category(null, "ANTES"));
        repository.save(new Category(null, "OCUPADA"));

        mockMvc.perform(put(URL + "/" + category.getId()).contentType(MediaType.APPLICATION_JSON).content(json("DESPUES")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.categoryName").value("DESPUES"));
        assertThat(repository.findById(category.getId())).get()
                .extracting(Category::getCategoryName).isEqualTo("DESPUES");

        // changing only the case of its own name is fine; taking another's is not
        mockMvc.perform(put(URL + "/" + category.getId()).contentType(MediaType.APPLICATION_JSON).content(json("Despues")))
                .andExpect(status().isOk());
        mockMvc.perform(put(URL + "/" + category.getId()).contentType(MediaType.APPLICATION_JSON).content(json("OCUPADA")))
                .andExpect(status().isConflict());
        mockMvc.perform(put(URL + "/999999").contentType(MediaType.APPLICATION_JSON).content(json("X")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deletesACategory() throws Exception {
        Category category = repository.save(new Category(null, "BORRAR"));

        mockMvc.perform(delete(URL + "/" + category.getId()))
                .andExpect(status().isNoContent());
        assertThat(repository.findById(category.getId())).isEmpty();

        mockMvc.perform(delete(URL + "/" + category.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void invalidRequestsAreRejected() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(" ")))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON).content("[]"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON).content("[" + json("") + "]"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void databaseEnforcesOneNamePerCategory() {
        repository.saveAndFlush(new Category(null, "UNICA"));

        assertThatThrownBy(() -> repository.saveAndFlush(new Category(null, "unica")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @WithMockUser(authorities = "USER")
    void usersCanListButNotManage() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json("NO")))
                .andExpect(status().isForbidden());
        mockMvc.perform(post(URL + "/batch").contentType(MediaType.APPLICATION_JSON).content("[" + json("NO") + "]"))
                .andExpect(status().isForbidden());
        mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(json("NO")))
                .andExpect(status().isForbidden());
        mockMvc.perform(delete(URL + "/1")).andExpect(status().isForbidden());
    }

    @Test
    void requiresAuthentication() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isUnauthorized());
    }

    private static String json(String name) {
        return "{\"categoryName\":\"%s\"}".formatted(name);
    }
}
