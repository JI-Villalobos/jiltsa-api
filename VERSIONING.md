# Versioning

The Jiltsa API follows [Semantic Versioning 2.0.0](https://semver.org): `MAJOR.MINOR.PATCH`.

## Where the version lives

`gradle.properties` is the only place the version is written. Everything else reads it:

| Consumer | How |
|---|---|
| `build.gradle` | Gradle picks `version` up from `gradle.properties`; the build fails if it is not a semantic version |
| Jar / `META-INF/build-info.properties` | `springBoot.buildInfo()` writes `build.version` |
| OpenAPI `info.version` (`/v3/api-docs`) | `OpenApiConfig` reads `BuildProperties` |
| `GET /jiltsa/api/v1/version` (public) | `VersionController` reads `BuildProperties` |
| Docker image tag | `JILTSA_VERSION=x.y.z docker compose build` (defaults to `latest`) |
| GitHub release | The `vX.Y.Z` tag must equal `gradle.properties`, or the workflow fails |

## What bumps what

The API contract is what clients depend on: paths, parameters, request and response bodies,
status codes, auth rules, and the database schema Flyway manages.

| Bump | When | Examples |
|---|---|---|
| **MAJOR** | A client or deployment that works today would break | Removing or renaming an endpoint, parameter or response field; changing a type or a status code; tightening auth on an existing endpoint; a migration that needs manual steps or cannot be applied to a live database; raising the required Java/MySQL version |
| **MINOR** | New capability, existing clients keep working unchanged | New endpoint; new optional parameter or request field; new response field; new additive Flyway migration; new configuration variable with a safe default |
| **PATCH** | Fixes and internal changes with no contract change | Bug fixes; performance; dependency/CVE updates; refactors; docs |

Rules of thumb:

- If you have to touch the frontend for the change to keep working, it is MAJOR.
- Adding a field to a response is MINOR; removing or renaming one is MAJOR.
- A new required request field is MAJOR (old clients would get 400).
- A Flyway migration is MINOR when additive and backward compatible, MAJOR otherwise.
- The `/v1` in the path is the MAJOR line for clients. It only changes when a new MAJOR must run
  side by side with the old one; a MAJOR bump that replaces the old contract keeps `/v1`.

Pre-releases are allowed (`2.2.0-rc.1`), but only tags without a pre-release suffix are deployed.

## Cutting a release

`gradle.properties` always holds the version **being developed** (the next release).
`CHANGELOG.md` collects its changes under `[Unreleased]`. Release when `main` is ready:

1. Check the bump: compare the `[Unreleased]` entries with the table above. If the planned
   version in `gradle.properties` is too low (an unreleased change turned out to be breaking),
   raise it now.
2. In `CHANGELOG.md`, rename `[Unreleased]` to `[X.Y.Z] - YYYY-MM-DD`, add an empty
   `[Unreleased]` above it and update the compare links at the bottom.
3. Commit (`release 2.1.0`) and merge to `main`.
4. Tag and push the tag:
   ```sh
   git tag -a v2.1.0 -m "2.1.0"
   git push origin v2.1.0
   ```
   The `Release` workflow checks tag ↔ `gradle.properties` ↔ `CHANGELOG.md`, runs the tests, builds
   the jar and publishes a GitHub release with that changelog section.
5. Deploy that tag (`JILTSA_VERSION=2.1.0 docker compose build`), then confirm with
   `GET /jiltsa/api/v1/version`.
6. Open the next cycle: bump `gradle.properties` to the smallest plausible next version
   (usually `X.Y+1.0`) in the first PR that adds a change.

## Each pull request

- Add an entry under `[Unreleased]` in `CHANGELOG.md` for anything a client or an operator would notice.
- If the change is breaking, say so in the entry (**Breaking:**) and make sure `gradle.properties`
  carries a MAJOR bump.
