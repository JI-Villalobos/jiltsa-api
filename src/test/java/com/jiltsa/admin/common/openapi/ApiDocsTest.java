package com.jiltsa.admin.common.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.matchesPattern;
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
    private static final String GET_ACCOUNT = "$.paths['/jiltsa/api/v1/accounts/account/{accountingId}'].get";
    private static final String SALES_BATCH = "$.paths['/jiltsa/api/v1/sales/batch'].post";
    private static final String LOGIN = "$.paths['/jiltsa/api/v1/auth/authenticate'].post";
    private static final String VERSION = "$.paths['/jiltsa/api/v1/version'].get";
    private static final String SEMVER = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(-[0-9A-Za-z.-]+)?(\\+[0-9A-Za-z.-]+)?$";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void docsAndUiArePublic() throws Exception {
        mockMvc.perform(get(DOCS)).andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Jiltsa API"))
                // The version Gradle wrote to build-info.properties, i.e. the one in gradle.properties
                .andExpect(jsonPath("$.info.version", matchesPattern(SEMVER)));
        mockMvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
    }

    @Test
    void versionEndpointIsPublicAndDocumentedAsSuch() throws Exception {
        mockMvc.perform(get(DOCS))
                .andExpect(jsonPath(VERSION + ".tags[0]").value("common"))
                .andExpect(jsonPath(VERSION + ".security.length()").value(0))
                .andExpect(jsonPath(VERSION + ".responses.401").doesNotExist())
                .andExpect(jsonPath(VERSION + ".responses.200.content['application/json'].schema.$ref")
                        .value("#/components/schemas/VersionDto"));
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

    @Test
    void operationsDescribeThemselvesAndTheirNotFoundAndConflictCases() throws Exception {
        mockMvc.perform(get(DOCS))
                .andExpect(jsonPath("$.tags[?(@.name=='cashproof')].description").isNotEmpty())
                .andExpect(jsonPath(RANGE + ".summary").value("List a branch's accountings within a date range"))
                .andExpect(jsonPath(RANGE + ".parameters[?(@.name=='initial')].description").value("Range start"))
                .andExpect(jsonPath(GET_ACCOUNT + ".responses.404.description").value("No accounting with that id"))
                .andExpect(jsonPath(GET_ACCOUNT + ".responses.404.content['application/problem+json'].schema.$ref")
                        .value("#/components/schemas/ProblemDetail"))
                .andExpect(jsonPath(GET_ACCOUNT + ".responses.200.content['application/json'].schema.$ref")
                        .value("#/components/schemas/AccountingDto"))
                .andExpect(jsonPath(SALES_BATCH + ".responses.409.content['application/problem+json'].schema.$ref")
                        .value("#/components/schemas/ProblemDetail"))
                .andExpect(jsonPath(SALES_BATCH + ".responses.201").exists());
    }

    @Test
    void everyOperationDocumentsItsSuccessResponse() throws Exception {
        String body = mockMvc.perform(get(DOCS)).andReturn().getResponse().getContentAsString();
        List<String> withoutSuccess = new ArrayList<>();
        new ObjectMapper().readTree(body).get("paths").properties().forEach(path ->
                path.getValue().properties().forEach(operation -> {
                    JsonNode responses = operation.getValue().get("responses");
                    boolean hasSuccess = false;
                    for (var codes = responses.fieldNames(); codes.hasNext(); ) {
                        hasSuccess |= codes.next().startsWith("2");
                    }
                    if (!hasSuccess) withoutSuccess.add(operation.getKey() + " " + path.getKey());
                }));
        assertThat(withoutSuccess).isEmpty();
    }
}
