# Dockerfile
FROM eclipse-temurin:17-jdk
VOLUME /tmp
ARG JAR_FILE=target/user-service-1.0.0.jar
COPY ${JAR_FILE} user-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar"]
