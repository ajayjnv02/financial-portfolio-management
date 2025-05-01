# 📊 Financial Portfolio Management System (Microservices-based)

A robust, event-driven, GraphQL-powered microservices architecture for managing user portfolios, investments, trades, alerts, and notifications. Deployed using a hybrid AWS approach (EKS + Fargate + Lambda).

---

## 📦 Microservices Overview

| Service                | Purpose                              | Tech Stack |
|------------------------|--------------------------------------|------------|
| `user-service`         | User registration & authentication   | Spring Boot, GraphQL, PostgreSQL, OAuth2 |
| `portfolio-service`    | Manage user portfolios               | Spring Boot, GraphQL, PostgreSQL |
| `investment-service`   | Track and analyze investments        | Spring Boot, GraphQL, Cassandra, Kafka |
| `trade-service`        | Trade execution & event sourcing     | Spring Boot, GraphQL, Kafka, PostgreSQL |
| `price-alert-service`  | Monitor prices & send alerts         | Spring Boot, Redis, Kafka, REST |
| `notification-service` | Send emails/SMS/push notifications   | AWS Lambda, SNS, SES |
| `market-data-service`  | Ingest price data from external APIs | Spring Boot, REST, Kafka, Cassandra |
| `saga-orchestrator`    | Handle distributed workflows         | Kafka / AWS Step Functions |
| `graphql-gateway`      | Unified GraphQL entry point          | Apollo Gateway / Spring GraphQL |

---

## 🔧 Tech Stack

- **Language**: Java 17
- **Frameworks**: Spring Boot 3, Spring GraphQL, Spring Data JPA
- **Message Broker**: Apache Kafka (Confluent MSK)
- **Database**:
  - PostgreSQL (User, Portfolio, Trade)
  - Cassandra / DynamoDB (Investments, Market Data)
  - Redis (Price Alerts)
- **Schema & APIs**:
  - GraphQL Federation (Apollo/Spring Gateway)
  - OpenAPI (Admin/Batch APIs)
  - Avro (Kafka event schemas)
  - Protobuf (optional, internal gRPC)
- **Security**: OAuth2 / JWT / Cognito
- **Infrastructure**:
  - EKS for stateful services
  - Fargate for stateless HTTP services
  - Lambda for alerts/notifications
  - S3, SNS, SES, RDS, ElastiCache, MSK

---

## 🔁 Interservice Communication

- **GraphQL Gateway** → GraphQL APIs
- **Kafka (Avro)** → Trade events, price updates, alert triggers
- **REST** → Admin and fallback APIs
- **Step Functions / Kafka** → Orchestration for sagas

---

## 🔐 Security

- OAuth2/JWT tokens propagated via GraphQL gateway
- IRSA (IAM roles for service accounts) on EKS
- AWS Secrets Manager for DB/API keys

---

## 🧪 Testing Strategy

- JUnit 5 + Mockito for unit tests
- TestContainers for Kafka/PostgreSQL integration
- Contract tests for GraphQL and OpenAPI
- Load tests via k6 / Gatling
- Monitoring via Prometheus, Grafana, CloudWatch

---

## 🚀 Dev & CI/CD

- Build: Maven multi-module setup
- CI: GitHub Actions or AWS CodePipeline
- Containerization: Docker + ECS/Fargate or EKS
- Terraform: VPC, RDS, MSK, ElastiCache, IAM, etc.

---

## 📁 Folder Structure

```
financial-portfolio-parent/
├── common-lib/
├── user-service/
├── portfolio-service/
├── investment-service/
├── trade-service/
├── price-alert-service/
├── notification-service/
├── market-data-service/
├── saga-orchestrator/
└── graphql-gateway/
```

---

## 📌 Sample API Endpoints

### GraphQL (`POST /graphql`)
```graphql
mutation {
  registerUser(name: "Ajay", email: "ajay@example.com", password: "secure123") {
    id
    name
  }
}

query {
  getPortfolio(userId: "123") {
    id
    name
    value
  }
}
```

### REST (Admin APIs)
```http
GET /api/v1/users          --> user-service
POST /api/v1/alerts        --> price-alert-service
GET /api/v1/prices/NIFTY   --> market-data-service
```

---

## 🖼️ Architecture Diagram

```
     [Client]
        |
     [GraphQL Gateway] <--- OAuth2 JWT
        |
 -----------------------------------------------------
 |      |        |         |        |        |       |
User  Portfolio  Investment  Trade  Price   Market  Orchestrator
Svc      Svc        Svc       Svc   Alert     Svc       Svc
                                 ↘          ↗
                              Kafka Topics (TRADE_EXECUTED, ALERT_TRIGGERED)
```

---

## 🐳 Docker & Kubernetes (EKS) Setup

1. **Docker Build**
```bash
docker build -t user-service ./user-service
docker build -t trade-service ./trade-service
...
```

2. **K8s Manifests**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: user-service
  template:
    metadata:
      labels:
        app: user-service
    spec:
      containers:
      - name: user-service
        image: ajay/user-service:latest
        ports:
        - containerPort: 8080
```

3. **Expose via ALB Ingress Controller**

4. **Use IRSA for secure AWS service access**

---

_Last updated: May 01, 2025_
