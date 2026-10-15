package com.jiltsa.admin.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Authorities are the bare Role names (see AppUser#getAuthorities), hence authorities = ... below. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class AuthorizationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "USER")
    void userCannotDelete() throws Exception {
        mockMvc.perform(delete("/jiltsa/api/v1/accounts/account/999999"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.title").value("Forbidden"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void userCannotRegisterAccounts() throws Exception {
        mockMvc.perform(post("/jiltsa/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"x\",\"email\":\"x@x.com\",\"pass\":\"secret\",\"branchId\":1}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void userCannotManageBranches() throws Exception {
        mockMvc.perform(post("/jiltsa/api/v1/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"nueva\",\"isActive\":true}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void userKeepsDailyOperations() throws Exception {
        mockMvc.perform(get("/jiltsa/api/v1/accounts/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void adminCanDelete() throws Exception {
        mockMvc.perform(delete("/jiltsa/api/v1/accounts/account/999999"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "DEVELOPER")
    void developerIsTreatedAsAdmin() throws Exception {
        mockMvc.perform(delete("/jiltsa/api/v1/accounts/account/999999"))
                .andExpect(status().isOk());
    }

    @Test
    void registerIsNotPublicAnymore() throws Exception {
        mockMvc.perform(post("/jiltsa/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"x\",\"email\":\"x@x.com\",\"pass\":\"secret\",\"branchId\":1}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticateStaysPublic() throws Exception {
        mockMvc.perform(post("/jiltsa/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"nobody@x.com\",\"pass\":\"wrong\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }
}
