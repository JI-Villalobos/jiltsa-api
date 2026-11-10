package com.jiltsa.admin.common.version;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class VersionControllerTest {
    private static final String VERSION = "/jiltsa/api/v1/version";
    private static final String SEMVER = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(-[0-9A-Za-z.-]+)?(\\+[0-9A-Za-z.-]+)?$";

    @Autowired
    private MockMvc mockMvc;
    /** Present whenever Gradle built the classes (springBoot.buildInfo()), which is how tests run. */
    @Autowired
    private BuildProperties build;

    @Test
    void versionIsPublicAndReportsTheBuiltSemanticVersion() throws Exception {
        mockMvc.perform(get(VERSION))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(build.getVersion()))
                .andExpect(jsonPath("$.version", matchesPattern(SEMVER)))
                .andExpect(jsonPath("$.buildTime").value(build.getTime().toString()));
    }

    @Test
    void onlyGetIsPublic() throws Exception {
        mockMvc.perform(post(VERSION))
                .andExpect(status().isUnauthorized());
    }
}
