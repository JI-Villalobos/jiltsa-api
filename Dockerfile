FROM gradle:7.5.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
ARG PORT
ARG JLTS_DATASOURCE_URL
ARG JLTS_DATASOURCE_USERNAME
ARG JLTS_DATASOURCE_PASSWORD
ARG JWT_KEY

ENV JLTS_DATASOURCE_URL=$JLTS_DATASOURCE_URL
ENV JLTS_DATASOURCE_USERNAME=$JLTS_DATASOURCE_USERNAME
ENV JLTS_DATASOURCE_PASSWORD=$JLTS_DATASOURCE_PASSWORD
ENV JWT_KEY=$JWT_KEY
ENV PORT=$PORT

WORKDIR /home/gradle/src
RUN gradle build -x test 

FROM eclipse-temurin:17-jdk-alpine
EXPOSE $PORT
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*1.1.0.jar /app/jiltsa-admin.jar

ENTRYPOINT ["java","-jar","/app/jiltsa-admin.jar"]
