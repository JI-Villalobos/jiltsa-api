package com.jiltsa.admin.common.version;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Public (no token) so clients and deploy checks can confirm which version is running before logging in.
 * The version is the one Gradle wrote to META-INF/build-info.properties from gradle.properties.
 */
@RestController
@RequestMapping("jiltsa/api/v1/version")
public class VersionController {
    static final String DEV_VERSION = "dev";
    private final VersionDto version;

    public VersionController(Optional<BuildProperties> build) {
        this.version = build
                .map(b -> new VersionDto(b.getVersion(), b.getTime()))
                .orElse(new VersionDto(DEV_VERSION, null));
    }

    @Operation(summary = "Version of the running API (semantic version MAJOR.MINOR.PATCH) and its build time")
    @GetMapping
    public VersionDto getVersion() {
        return version;
    }
}
