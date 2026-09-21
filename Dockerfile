# Build stage: no secrets here. Configuration is injected at run time only.
FROM gradle:8.14-jdk21 AS build
WORKDIR /home/gradle/src

# Resolve dependencies in their own layer so source changes don't re-download them
COPY --chown=gradle:gradle build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon -q > /dev/null || true

COPY --chown=gradle:gradle src ./src
RUN gradle bootJar --no-daemon

# Runtime stage: JRE only, unprivileged user
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app && adduser -S app -G app
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/jiltsa-admin.jar
USER app

# The app listens on $PORT (default 8080). Required at run time:
#   JLTS_DATASOURCE_URL, JLTS_DATASOURCE_USERNAME, JLTS_DATASOURCE_PASSWORD, JWT_KEY
# Optional: JWT_EXPIRATION (default 12h), APP_CORS_ALLOWED_ORIGINS (default localhost:3000/3001),
#   APP_PHARMACY_EXPENSE_TYPE_ID (default 6), APP_PHARMACY_OPERATIVE_EXPENSE_CATEGORY
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/jiltsa-admin.jar"]
