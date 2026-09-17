package com.jiltsa.admin.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void missingResourceReturnsProblemDetail404() throws Exception {
        mockMvc.perform(get("/jiltsa/api/v1/branches/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.detail").value("Branch not found: 999999"));
    }

    @Test
    @WithMockUser
    void badPathVariableTypeReturns400() throws Exception {
        mockMvc.perform(get("/jiltsa/api/v1/branches/not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void malformedBearerTokenIsRejectedWithoutServerError() throws Exception {
        mockMvc.perform(get("/jiltsa/api/v1/branches")
                        .header("Authorization", "Bearer not.a.jwt"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void missingRequiredFieldsReturn400() throws Exception {
        mockMvc.perform(post("/jiltsa/api/v1/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @WithMockUser
    void invalidElementInListBodyReturns400() throws Exception {
        mockMvc.perform(post("/jiltsa/api/v1/bills/save-all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{}]"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void blankCredentialsReturn400() throws Exception {
        mockMvc.perform(post("/jiltsa/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"\",\"pass\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
