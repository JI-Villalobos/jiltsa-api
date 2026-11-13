package com.jiltsa.admin.seller.controller;

import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.entity.SellerProfile;
import com.jiltsa.admin.seller.persistence.repository.SellerProfileRepository;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import com.jiltsa.admin.user.AppUser;
import com.jiltsa.admin.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Each test creates its own sellers, so the one-profile-per-seller rule does not couple tests.
 * Sellers send the form as an AppUser of their branch (userOfBranch below); admins use @WithMockUser.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class SellerProfileControllerTest {
    private static final String URL = "/jiltsa/api/v1/seller-profiles";

    @Autowired private MockMvc mockMvc;
    @Autowired private SellerRepository sellerRepository;
    @Autowired private SellerProfileRepository repository;

    private Seller seller(String name, int branchId) {
        return sellerRepository.save(new Seller(name, branchId, true));
    }

    /** The authentication the JWT filter builds for a USER account of the branch. */
    private static RequestPostProcessor userOfBranch(String email, int branchId) {
        AppUser user = AppUser.builder().email(email).username(email).branchId(branchId).role(Role.USER).build();
        return authentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }

    /** The form as the frontend would send it; rfc/curp in lower case to check they are normalized. */
    private static String json(Integer sellerId, String fullName, String rfc, String curp, double salary) {
        return "{\"sellerId\":" + sellerId + ",\"fullName\":\"" + fullName + "\",\"birthDate\":\"1995-04-12\","
                + "\"phone\":\"871 123 4567\"," + (rfc == null ? "" : "\"rfc\":\"" + rfc + "\",")
                + (curp == null ? "" : "\"curp\":\"" + curp + "\",")
                + "\"emergencyContactName\":\"Rosa Pérez\",\"emergencyContactPhone\":\"+52 871 765 4321\","
                + "\"hireDate\":\"2024-02-01\",\"position\":\"Vendedora\",\"salary\":" + salary + ",\"privacyNoticeAccepted\":true}";
    }

    private static int idOf(String body) {
        return Integer.parseInt(body.replaceAll("^\\{\"id\":(\\d+).*", "$1"));
    }

    @Test
    void theSellerSendsHerOwnForm() throws Exception {
        Seller seller = seller("Laura Crear", 1);

        mockMvc.perform(post(URL).with(userOfBranch("laura@jiltsa.com", 1)).contentType(MediaType.APPLICATION_JSON)
                        .content(json(seller.getId(), "Laura Crear López", "crel950412ab1", "crel950412mcllpr09", 9500.5)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.sellerId").value(seller.getId()))
                .andExpect(jsonPath("$.branchId").value(1))
                .andExpect(jsonPath("$.fullName").value("Laura Crear López"))
                .andExpect(jsonPath("$.birthDate").value("1995-04-12"))
                .andExpect(jsonPath("$.rfc").value("CREL950412AB1"))
                .andExpect(jsonPath("$.curp").value("CREL950412MCLLPR09"))
                .andExpect(jsonPath("$.emergencyContactPhone").value("+52 871 765 4321"))
                .andExpect(jsonPath("$.hireDate").value("2024-02-01"))
                .andExpect(jsonPath("$.salary").value(9500.5))
                .andExpect(jsonPath("$.privacyNoticeAccepted").value(true))
                .andExpect(jsonPath("$.privacyNoticeAcceptedAt").isString())
                .andExpect(jsonPath("$.registeredBy").value("laura@jiltsa.com"))
                .andExpect(jsonPath("$.createdAt").isString())
                .andExpect(jsonPath("$.updatedAt").isString());
        assertThat(repository.findBySellerId(seller.getId())).isPresent();

        // rfc and curp are optional, as in the form
        Seller other = seller("Sin RFC", 1);
        mockMvc.perform(post(URL).with(userOfBranch("otra@jiltsa.com", 1)).contentType(MediaType.APPLICATION_JSON)
                        .content(json(other.getId(), "Sin RFC", null, null, 8000)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rfc").doesNotExist())
                .andExpect(jsonPath("$.curp").doesNotExist());
    }

    @Test
    void theNoticeMustBeAcceptedAndASellerOnlyRegistersHerOwnBranch() throws Exception {
        Seller seller = seller("Nora Aviso", 2);

        mockMvc.perform(post(URL).with(userOfBranch("nora@jiltsa.com", 2)).contentType(MediaType.APPLICATION_JSON)
                        .content(json(seller.getId(), "Nora", null, null, 1).replace("\"privacyNoticeAccepted\":true", "\"privacyNoticeAccepted\":false")))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL).with(userOfBranch("ajena@jiltsa.com", 3)).contentType(MediaType.APPLICATION_JSON)
                        .content(json(seller.getId(), "Nora", null, null, 1)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail", containsString("another branch")));
        assertThat(repository.findBySellerId(seller.getId())).isEmpty();
    }

    @Test
    @WithMockUser(username = "admin@jiltsa.com", authorities = "ADMIN")
    void anAdminRegistersEmployeesWhoAnsweredThePreviousForm() throws Exception {
        Seller seller = seller("Registrada Por Admin", 3);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(seller.getId(), "Registrada", null, null, 1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registeredBy").value("admin@jiltsa.com"))
                .andExpect(jsonPath("$.branchId").value(3));
        // ...only with the notice accepted in that form
        Seller other = seller("Sin Aviso", 3);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(json(other.getId(), "Sin Aviso", null, null, 1).replace("\"privacyNoticeAccepted\":true", "\"privacyNoticeAccepted\":false")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void rejectsUnknownSellerSecondProfileAndInvalidFields() throws Exception {
        Seller seller = seller("Marta Rechazo", 2);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(999999, "X", null, null, 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Seller not found: 999999"));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(seller.getId(), "Marta", null, null, 1)))
                .andExpect(status().isCreated());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(seller.getId(), "Marta otra vez", null, null, 1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("already has profile")));

        Seller fresh = seller("Campos", 2);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(fresh.getId(), "Campos", "NO-ES-RFC", null, 1)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(fresh.getId(), "Campos", null, "123", 1)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(fresh.getId(), "Campos", null, null, -1)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(json(fresh.getId(), "Campos", null, null, 1).replace("\"hireDate\":\"2024-02-01\"", "\"hireDate\":\"2999-01-01\"")))
                .andExpect(status().isBadRequest());
        assertThat(repository.findBySellerId(fresh.getId())).isEmpty();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getsByIdBySellerAndByBranch() throws Exception {
        Seller first = seller("Ana Sucursal Tres", 3);
        Seller second = seller("Beatriz Sucursal Tres", 3);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(second.getId(), "Zoe Lista", null, null, 1)))
                .andExpect(status().isCreated());
        String body = mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(first.getId(), "Ana Lista", null, null, 1)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        int profileId = idOf(body);

        mockMvc.perform(get(URL + "/" + profileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Ana Lista"));
        mockMvc.perform(get(URL + "/by-seller/" + first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(profileId));
        mockMvc.perform(get(URL + "/by-branch/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.fullName=='Ana Lista')]").exists())
                .andExpect(jsonPath("$[?(@.fullName=='Zoe Lista')]").exists())
                .andExpect(jsonPath("$[*].branchId", everyItem(is(3))));
        mockMvc.perform(get(URL + "/999999")).andExpect(status().isNotFound());
        mockMvc.perform(get(URL + "/by-seller/999999")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void updatesEveryFormFieldButNotTheSeller() throws Exception {
        Seller seller = seller("Carla Actualizar", 1);
        Seller another = seller("Otra Vendedora", 1);
        String body = mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json(seller.getId(), "Carla", null, null, 7000)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        int profileId = idOf(body);

        mockMvc.perform(put(URL + "/" + profileId).contentType(MediaType.APPLICATION_JSON)
                        .content(json(seller.getId(), "Carla Actualizada", "AACC950412XY9", null, 8200)
                                .replace("\"position\":\"Vendedora\"", "\"position\":\"Encargada\"")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(profileId))
                .andExpect(jsonPath("$.fullName").value("Carla Actualizada"))
                .andExpect(jsonPath("$.rfc").value("AACC950412XY9"))
                .andExpect(jsonPath("$.position").value("Encargada"))
                .andExpect(jsonPath("$.salary").value(8200.0));
        assertThat(repository.findById(profileId)).get().extracting(SellerProfile::getPosition).isEqualTo("Encargada");

        mockMvc.perform(put(URL + "/" + profileId).contentType(MediaType.APPLICATION_JSON).content(json(another.getId(), "Carla", null, null, 1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("cannot be moved")));
        mockMvc.perform(put(URL + "/999999").contentType(MediaType.APPLICATION_JSON).content(json(seller.getId(), "X", null, null, 1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void aSellerReadsHerOwnProfileBySeller() throws Exception {
        Seller seller = seller("Carla Consulta", 2);
        String body = mockMvc.perform(post(URL).with(userOfBranch("carla@jiltsa.com", 2)).contentType(MediaType.APPLICATION_JSON)
                        .content(json(seller.getId(), "Carla Consulta Ruiz", null, null, 1)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(get(URL + "/by-seller/" + seller.getId()).with(userOfBranch("carla@jiltsa.com", 2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idOf(body)))
                .andExpect(jsonPath("$.fullName").value("Carla Consulta Ruiz"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void listingAndEditingProfilesIsAdminOnly() throws Exception {
        mockMvc.perform(get(URL + "/by-branch/1")).andExpect(status().isForbidden());
        mockMvc.perform(get(URL + "/1")).andExpect(status().isForbidden());
        mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(json(1, "X", null, null, 1)))
                .andExpect(status().isForbidden());
    }
}
