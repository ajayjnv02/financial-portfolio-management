# 📊 Financial Portfolio Management System (Microservices-based)

A robust, event-driven, GraphQL-powered microservices architecture for managing user portfolios, investments, trades, alerts, and notifications. Deployed using a hybrid AWS setup and **Apollo GraphQL Gateway** in Node.js.

---

## 📦 Microservices Overview

| Service                | Purpose                              | Tech Stack |
|------------------------|--------------------------------------|------------|
| `user-service`         | User registration & authentication   | Spring Boot, GraphQL subgraph, PostgreSQL, OAuth2 |
| `portfolio-service`    | Manage user portfolios               | Spring Boot, GraphQL subgraph, PostgreSQL |
| `investment-service`   | Track and analyze investments        | Spring Boot, GraphQL subgraph, Cassandra, Kafka |
| `trade-service`        | Trade execution & event sourcing     | Spring Boot, GraphQL subgraph, Kafka, PostgreSQL |
| `price-alert-service`  | Monitor prices & send alerts         | Spring Boot, Redis, Kafka, REST |
| `notification-service` | Send emails/SMS/push notifications   | AWS Lambda, SNS, SES |
| `market-data-service`  | Ingest price data from external APIs | Spring Boot, REST, Kafka, Cassandra |
| `saga-orchestrator`    | Handle distributed workflows         | Kafka / AWS Step Functions |
| `graphql-gateway`      | Centralized Apollo GraphQL Gateway   | Node.js, Apollo Server 4, Federation v2 |

---

## 🔧 Tech Stack

- **Language**: Java 17 (backend), Node.js (Apollo Gateway)
- **Frameworks**: Spring Boot 3, Spring GraphQL, Apollo Server 4
- **Message Broker**: Apache Kafka (Confluent MSK)
- **Database**:
    - PostgreSQL (User, Portfolio, Trade)
    - Cassandra / DynamoDB (Investments, Market Data)
    - Redis (Price Alerts)
- **Schema & APIs**:
    - GraphQL subgraphs (Java microservices)
    - Apollo Federation Gateway (Node.js)
    - REST (Admin APIs)
    - Avro (Kafka event schemas)
- **Security**: OAuth2 / JWT / Cognito
- **Infrastructure**:
    - EKS for stateful services
    - Fargate for stateless APIs
    - Lambda for event-driven alerts
    - S3, SNS, SES, RDS, ElastiCache, MSK

---

## 🔁 Interservice Communication

- **Apollo Gateway** → Federated GraphQL APIs
- **Kafka (Avro)** → Trade events, price updates, alert triggers
- **REST** → Admin and fallback APIs
- **Step Functions / Kafka** → Orchestration for sagas

---

## 🔐 Security

- OAuth2/JWT tokens propagated via Apollo Gateway
- IRSA (IAM roles for service accounts) on EKS
- AWS Secrets Manager for DB/API keys

---

## 🧪 Testing Strategy

- JUnit 5 + Mockito for unit tests
- TestContainers for Kafka/PostgreSQL integration
- GraphQL contract tests
- Load tests via k6 / Gatling
- Monitoring via Prometheus, Grafana, CloudWatch

---

## 🚀 Dev & CI/CD

- Build: Maven multi-module setup
- CI: GitHub Actions or AWS CodePipeline
- Containerization: Docker + ECS/Fargate or EKS
- Terraform: VPC, RDS, MSK, ElastiCache, IAM, etc.
- Apollo Gateway: Node.js + Federation schema polling

---

## 📁 Folder Structure

```
financial-portfolio-parent/
├── apollo-gateway/              # Apollo Server (Node.js)
├── common-lib/
├── user-service/
├── portfolio-service/
├── investment-service/
├── trade-service/
├── price-alert-service/
├── notification-service/
├── market-data-service/
├── saga-orchestrator/
```

---

## 📌 API Endpoints

### Apollo Gateway (exposed to clients)
```
POST /graphql     → Federated schema combining all services
```

### GraphQL Subgraphs (internal services)
```
POST http://user-service/graphql        → user-service
POST http://portfolio-service/graphql   → portfolio-service
```

### REST (Admin APIs)
```
GET /api/v1/users/<built-in function id>
POST /api/v1/alerts
```

---

## 🖼️ Federation Architecture Diagram (Text)

```
[Client]
   |
[Apollo Server - GraphQL Gateway]
   |         |           |
User-MS   Portfolio-MS  Trade-MS
  /graphql    /graphql    /graphql
(Spring Boot Subgraphs)
```

---

## 🐳 Docker & Kubernetes (EKS) Setup

1. Docker Build:
```bash
docker build -t user-service ./user-service
docker build -t apollo-gateway ./apollo-gateway
```

2. K8s Deployments:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: apollo-gateway
spec:
  replicas: 2
  template:
    spec:
      containers:
      - name: gateway
        image: your-registry/apollo-gateway:latest
        ports:
        - containerPort: 4000
```

---

_Last updated: May 02, 2025_
