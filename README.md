# Order Service

![CI Pipeline](https://github.com/ahmedburale/order-service/actions/workflows/ci.yml/badge.svg)

A production-ready order management microservice built with Spring Boot 3.5, containerized with Docker, and deployed to Kubernetes.

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.5.14
- **Database:** PostgreSQL 16
- **ORM:** Spring Data JPA / Hibernate
- **API Docs:** Springdoc OpenAPI (Swagger UI)
- **Health Monitoring:** Spring Boot Actuator
- **Containerization:** Docker (multi-stage build)
- **Orchestration:** Kubernetes (Minikube)
- **CI/CD:** GitHub Actions

## Architecture

The project follows a **layered architecture** pattern:

```
├── Controller Layer    → REST API endpoints
├── Service Layer       → Business logic
├── Repository Layer    → Database access (Spring Data JPA)
└── Entity Layer        → Data models
```

## API Endpoints

| Method | Endpoint             | Description          |
|--------|----------------------|----------------------|
| POST   | `/api/orders`        | Create a new order   |
| GET    | `/api/orders`        | List all orders      |
| GET    | `/api/orders/{id}`   | Get order by ID      |
| PUT    | `/api/orders/{id}`   | Update an order      |
| DELETE | `/api/orders/{id}`   | Delete an order      |

### Health Endpoints

| Endpoint                          | Description              |
|-----------------------------------|--------------------------|
| `/actuator/health`                | Overall health status    |
| `/actuator/health/liveness`       | Kubernetes liveness probe  |
| `/actuator/health/readiness`      | Kubernetes readiness probe |

## Prerequisites

- Java 17
- Docker Desktop
- Minikube
- kubectl

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ahmedburale/order-service.git
cd order-service
```

### 2. Start PostgreSQL

```bash
docker run --name postgres-local \
  -e POSTGRES_DB=orderdb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 -d postgres:16-alpine
```

### 3. Run Locally

```bash
./mvnw clean compile
./mvnw spring-boot:run
```

Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to access the API documentation.

### 4. Build and Run with Docker

```bash
docker network create order-network
docker network connect order-network postgres-local

docker build -t order-service:latest .
docker run -d --name order-service \
  --network order-network \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-local:5432/orderdb \
  order-service:latest
```

### 5. Deploy to Kubernetes (Minikube)

```bash
minikube start
minikube image load order-service:latest
kubectl apply -f k8s/
minikube service order-service
```

## Docker

The project uses a **multi-stage Dockerfile** for optimized image builds:

- **Stage 1 (Builder):** Uses `eclipse-temurin:17-jdk` to compile and package the application
- **Stage 2 (Runtime):** Uses the lightweight `eclipse-temurin:17-jre` with a non-root user for security

## CI/CD Pipeline

Every push to `main` triggers a GitHub Actions workflow that:

1. Checks out the code
2. Sets up Java 17 with Maven caching
3. Runs unit tests
4. Builds the Docker image
5. Starts PostgreSQL and the app on a shared network
6. Verifies the container passes a health check

## Future Improvements

- Add Spring Security with JWT authentication
- Add user accounts and role-based authorization
- Replace `ddl-auto=update` with Flyway database migrations
- Add unit tests for the service layer
- Add integration tests with Testcontainers
- Add centralized logging
- Add Prometheus and Grafana monitoring
- Deploy the application to AWS or another cloud provider
- Add Helm charts for Kubernetes deployment

## License

This project is for educational purposes as part of the [NextWork](https://learn.nextwork.org) platform.
