package com.jiltsa.admin.common.version;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * What GET /version answers. {@code version} is the semantic version from gradle.properties
 * (see VERSIONING.md); {@code buildTime} is when that build was made, null when the app runs
 * straight from an IDE without the Gradle build-info step.
 */
public record VersionDto(
        @Schema(description = "Semantic version MAJOR.MINOR.PATCH of the running API", example = "2.1.0") String version,
        @Schema(description = "When this build was produced; null when not built by Gradle", nullable = true) Instant buildTime) {
}
