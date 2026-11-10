# Changelog

All notable changes to the Jiltsa API are recorded here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and the project uses
[Semantic Versioning](https://semver.org/spec/v2.0.0.html); see VERSIONING.md for
what counts as a breaking change in this API and how a release is cut.

## [Unreleased]

### Added
- `GET /jiltsa/api/v1/version`: public endpoint reporting the running version and build time.
- `GET /products/branch/{id}` and `POST /products/branch/{id}/lookup` (#3).
- Categories: list, create one or a batch, rename and delete (#4, migration V8).

### Changed
- The version now lives in `gradle.properties` and must be a valid semantic version;
  the build fails otherwise. `docker-compose.yml` tags the image from `JILTSA_VERSION`.
- Releases are cut by pushing a `vMAJOR.MINOR.PATCH` tag; the release workflow checks that
  the tag matches `gradle.properties` and that this file has a section for it.

## [2.0.0] - 2026-10-15

Spring Boot 3.5 rewrite of the 1.x API (#2). Clients of 1.x must re-check every call.

### Added
- OpenAPI description at `/v3/api-docs` and Swagger UI at `/swagger-ui.html`, every endpoint documented.
- `POST /sales/batch` and `POST /products/batch` for POS uploads; re-uploaded sale lines are rejected (409).
- `GET /sales-projections/daily` (30-day sales per active branch) and `GET /sales-projections/hourly`.
- Flyway owns the schema (`db/migration`); Hibernate only validates it.
- Request bodies are validated (`400` with a ProblemDetail naming the field).
- ArchUnit tests lock the module structure in.

### Changed
- **Breaking:** errors are RFC 7807 `application/problem+json`; missing resources answer 404 instead of 200 with a null body.
- **Breaking:** management and delete endpoints require the ADMIN role (403); unauthenticated requests get 401.
- **Breaking:** `GET /branches` lists only active branches.
- **Breaking:** cash withdrawals are referenced by branch id.
- **Breaking:** `Profile` and `CheckType` are stored by name instead of ordinal (migration V3).
- Stored procedures replaced with queries; the pharmacy cost concept is named once.
- JWT lifetime and CORS origins are configurable (`JWT_EXPIRATION`, `APP_CORS_ALLOWED_ORIGINS`) with tighter defaults.
- Paging accepts the legacy `page`, `elements`, `sortBy`, `sortDirection` parameters and honours the direction.

### Removed
- Unused plaintext `seller.password` column (migration V2).

### Security
- Patched Tomcat, Log4j and Jackson versions with open CVEs; `jwt.key` is required at startup.

## Before 2.0.0

The 1.x line (branches `jiltsa-1.1.0` … `jiltsa-1.7.0`) was never tagged. Its history is only in git.

[Unreleased]: https://github.com/JI-Villalobos/jiltsa-api/compare/v2.0.0...HEAD
[2.0.0]: https://github.com/JI-Villalobos/jiltsa-api/releases/tag/v2.0.0
