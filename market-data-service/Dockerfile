# Stage 1: Builder
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests -pl market-data-service -am

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/market-data-service/target/market-data-service*.jar market-data-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "market-data-service.jar"]
