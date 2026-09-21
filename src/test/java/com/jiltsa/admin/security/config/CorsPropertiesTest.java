package com.jiltsa.admin.security.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

class CorsPropertiesTest {

    private CorsProperties bind(MockEnvironment environment) {
        return new Binder(ConfigurationPropertySources.get(environment))
                .bindOrCreate("app.cors", CorsProperties.class);
    }

    @Test
    void unsetOriginsFallBackToLocalhostNeverToWildcard() {
        CorsProperties properties = bind(new MockEnvironment());

        assertThat(properties.allowedOrigins())
                .containsExactly("http://localhost:3000", "http://localhost:3001")
                .doesNotContain("*");
    }

    @Test
    void configuredOriginsReplaceTheDefault() {
        MockEnvironment environment = new MockEnvironment()
                .withProperty("app.cors.allowed-origins", "https://admin.jiltsa.mx,https://caja.jiltsa.mx");

        CorsProperties properties = bind(environment);

        assertThat(properties.allowedOrigins())
                .containsExactly("https://admin.jiltsa.mx", "https://caja.jiltsa.mx");
    }
}
