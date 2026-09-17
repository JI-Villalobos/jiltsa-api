package com.jiltsa.admin.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
