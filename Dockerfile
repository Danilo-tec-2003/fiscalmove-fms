FROM gradle:7.6.4-jdk8 AS build
WORKDIR /workspace

COPY build.gradle gradlew gradlew.bat ./
COPY gradle ./gradle
RUN chmod +x gradlew

COPY src ./src
RUN ./gradlew clean war --no-daemon

FROM tomcat:9.0-jdk8-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /workspace/build/libs/*.war /usr/local/tomcat/webapps/SISTEMA-FRETES.war

EXPOSE 8080
