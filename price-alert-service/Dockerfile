# Stage 1: Builder
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests -pl price-alert-service -am

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/price-alert-service/target/price-alert-service*.jar price-alert-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "price-alert-service.jar"]
