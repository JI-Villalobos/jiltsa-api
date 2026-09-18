package com.jiltsa.admin.common.openapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class ApiDocsTest {
    private static final String DOCS = "/v3/api-docs";
    private static final String RANGE = "$.paths['/jiltsa/api/v1/accounts/range'].get";
    private static final String DELETE_ACCOUNT = "$.paths['/jiltsa/api/v1/accounts/account/{accountingId}'].delete";
    private static final String LOGIN = "$.paths['/jiltsa/api/v1/auth/authenticate'].post";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void docsAndUiArePublic() throws Exception {
        mockMvc.perform(get(DOCS)).andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Jiltsa API"))
                .andExpect(jsonPath("$.info.version").value("2.0.0"));
        mockMvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
    }

    @Test
    void bearerJwtIsTheDefaultSecurityExceptForLogin() throws Exception {
        mockMvc.perform(get(DOCS))
                .andExpect(jsonPath("$.components.securitySchemes.bearerAuth.scheme").value("bearer"))
                .andExpect(jsonPath("$.security[0].bearerAuth").exists())
                .andExpect(jsonPath(LOGIN + ".security.length()").value(0))
                .andExpect(jsonPath(LOGIN + ".responses.401.description").value("Wrong credentials"))
                .andExpect(jsonPath(RANGE + ".responses.401.content['application/problem+json'].schema.$ref")
                        .value("#/components/schemas/ProblemDetail"));
    }

    @Test
    void pagingIsDocumentedWithTheParametersTheApiActuallyReads() throws Exception {
        mockMvc.perform(get(DOCS))
                .andExpect(jsonPath(RANGE + ".parameters[*].name",
                        containsInAnyOrder("initial", "end", "branchId", "page", "elements", "sortBy", "sortDirection")))
                .andExpect(jsonPath(RANGE + ".parameters[?(@.name=='elements')].schema.default").value(12))
                .andExpect(jsonPath(RANGE + ".parameters[?(@.name=='sortBy')].schema.default").value("date"))
                .andExpect(jsonPath(RANGE + ".parameters[?(@.name=='sortDirection')].schema.enum[*]", hasItem("desc")))
                .andExpect(jsonPath("$.paths['/jiltsa/api/v1/accounts/by-page'].get.parameters[?(@.name=='sortDirection')].schema.default")
                        .value("desc"));
    }

    @Test
    void operationsAreTaggedByModuleAndAdminEndpointsDeclare403() throws Exception {
        mockMvc.perform(get(DOCS))
                .andExpect(jsonPath(RANGE + ".tags[0]").value("cashproof"))
                .andExpect(jsonPath("$.paths['/jiltsa/api/v1/bills'].get.tags[0]").value("billing"))
                .andExpect(jsonPath(DELETE_ACCOUNT + ".responses.403.description").value("Requires the ADMIN role"))
                .andExpect(jsonPath(RANGE + ".responses.403").doesNotExist())
                .andExpect(jsonPath("$.paths['/jiltsa/api/v1/branches'].post.responses.400").exists())
                .andExpect(jsonPath("$.paths['/jiltsa/api/v1/bills/save-all'].post.responses.400").exists())
                .andExpect(jsonPath(RANGE + ".responses.200.content['application/json'].schema.$ref")
                        .value("#/components/schemas/PageAccountingDto"));
    }
}
