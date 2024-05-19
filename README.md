# Financial Portfolio Management System

## Overview

The Financial Portfolio Management System is a comprehensive solution designed to help users manage their financial portfolios efficiently. It includes services for user management, portfolio management, investment tracking, trade execution, notifications, and price alerts. The system leverages both RDBMS and NoSQL databases to handle different types of data storage requirements and ensures a scalable and robust architecture.

## Features

- **User Management**: Handles user registration, authentication, and profile management.
- **Portfolio Management**: Allows users to create, update, and delete financial portfolios.
- **Investment Tracking**: Tracks investments within portfolios, including additions, updates, and deletions.
- **Trade Execution**: Manages the execution, updating, and deletion of trades.
- **Notifications**: Sends notifications to users regarding their portfolio activities.
- **Price Alerts**: Alerts users when specified price conditions are met.
- **Event Management**: Uses Kafka for event streaming and asynchronous communication between microservices.
- **Caching**: Utilizes Redis for caching frequently accessed data to improve performance.
- **Saga Orchestration**: Manages distributed transactions across microservices.
- **Message Queuing**: Uses RabbitMQ for reliable message delivery and processing.
- **API Gateway**: Centralized entry point for managing and routing API requests.

## Microservices Architecture

The system is built using a microservices architecture, with each service handling a specific domain of functionality. The services communicate with each other through REST APIs, events, and message queues.

### Services and Database Types

| Service               | Description                             | Database Type |
|-----------------------|-----------------------------------------|---------------|
| User Service          | Manages user information and authentication | RDBMS         |
| Portfolio Service     | Manages portfolios and related data     | RDBMS         |
| Investment Service    | Tracks investments in portfolios        | RDBMS         |
| Trade Service         | Manages trade executions and updates    | RDBMS         |
| Notification Service  | Sends notifications to users            | NoSQL         |
| Price Alert Service   | Manages price alerts                    | NoSQL         |

## Technologies Used

- **Java**: The primary programming language for the microservices.
- **Spring Boot**: Framework for building and running the microservices.
- **MongoDB**: NoSQL database for storing notifications and price alerts.
- **MySQL**: RDBMS for storing user, portfolio, investment, and trade data.
- **AWS**: Infrastructure for hosting the application, including AWS DynamoDB, RDS, and other services.
- **Docker**: Containerization of microservices for easy deployment.
- **Kubernetes**: Orchestration of Docker containers.
- **Kafka**: Event streaming platform for handling asynchronous communication between microservices.
- **RabbitMQ**: Message broker for reliable message delivery and processing.
- **Redis**: In-memory data structure store for caching.
- **API Gateway**: Centralized entry point for managing and routing API requests.
- **Saga Pattern**: Implementation of distributed transactions across microservices.

## Setup and Installation

### Prerequisites

- Java 8 or higher
- Maven or Gradle
- Docker
- Kubernetes
- MongoDB
- MySQL
- Redis
- Kafka
- RabbitMQ
- AWS CLI

### Clone the Repository

```sh
git clone https://github.com/your-username/financial-portfolio-management.git
cd financial-portfolio-management
