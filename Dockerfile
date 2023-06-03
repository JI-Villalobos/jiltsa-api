FROM gradle:7.5.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test 

FROM openjdk:17-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar /app/jiltsa-admin.jar

ENTRYPOINT ["java","-jar","/app/jiltsa-admin.jar"]
