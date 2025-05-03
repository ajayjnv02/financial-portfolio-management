# Stage 1: Builder
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests -pl notification-service -am

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/notification-service/target/notification-service*.jar notification-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
