package com.jiltsa.admin.security.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "app.cors.allowed-origins=https://admin.jiltsa.mx,https://*.jiltsa.dev")
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class CorsConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void configuredOriginPassesPreflight() throws Exception {
        mockMvc.perform(options("/jiltsa/api/v1/branches")
                        .header("Origin", "https://admin.jiltsa.mx")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://admin.jiltsa.mx"));
    }

    @Test
    void wildcardPatternMatchesSubdomains() throws Exception {
        mockMvc.perform(options("/jiltsa/api/v1/branches")
                        .header("Origin", "https://staging.jiltsa.dev")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://staging.jiltsa.dev"));
    }

    @Test
    void otherOriginIsRejected() throws Exception {
        mockMvc.perform(options("/jiltsa/api/v1/branches")
                        .header("Origin", "https://evil.example.com")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isForbidden());
    }
}
