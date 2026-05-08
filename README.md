# Order Service

![CI Pipeline](https://github.com/ahmedburale/order-service/actions/workflows/ci.yml/badge.svg)

A production-ready order management microservice built with Spring Boot 3.4, containerized with Docker, and deployed to Kubernetes.

---

## Project Overview

The Order Service is a backend REST API for managing customer orders. This project demonstrates how a modern Spring Boot application can be developed using clean layered architecture, connected to PostgreSQL, documented with Swagger UI, containerized with Docker, deployed to Kubernetes, monitored with Spring Boot Actuator health probes, and validated through GitHub Actions CI/CD.

The goal of this project is to practice building a production-style backend service that follows professional software engineering standards and can be presented as a strong portfolio project.

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4 |
| API | Spring Web REST |
| Database | PostgreSQL |
| Persistence | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation |
| API Documentation | Swagger UI / Springdoc OpenAPI |
| Monitoring | Spring Boot Actuator |
| Containerization | Docker |
| Orchestration | Kubernetes / Minikube |
| CI/CD | GitHub Actions |
| Code Quality | Qodana |
| Build Tool | Maven |
| Version Control | Git / GitHub |

---

## Features

- Create customer orders
- Retrieve all orders
- Retrieve an order by ID
- Update existing orders
- Delete orders
- Validate client request payloads using DTOs
- Return clean error responses for validation and not-found errors
- Persist order data in PostgreSQL
- Generate interactive API documentation with Swagger UI
- Expose Actuator health, liveness, and readiness endpoints
- Build the application as a Docker image
- Run the API and PostgreSQL together using Docker networking
- Deploy PostgreSQL and the order service to Kubernetes
- Configure Kubernetes liveness and readiness probes
- Support safer deployments with Kubernetes rolling updates
- Run CI checks automatically on every push
- Run Qodana code quality analysis

---

## Architecture

This project follows a clean layered backend architecture.

```text
Controller Layer
    Handles HTTP requests and responses.

Service Layer
    Contains the business logic for creating, retrieving, updating, and deleting orders.

Repository Layer
    Communicates with PostgreSQL using Spring Data JPA.

Model Layer
    Defines the Order entity mapped to the database.

DTO Layer
    Defines and validates request data sent by clients.

Exception Handling Layer
    Provides consistent error responses for failed requests.
```

This structure keeps the application organized, testable, and easier to maintain.

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/orders` | Create a new order |
| GET | `/api/orders` | Retrieve all orders |
| GET | `/api/orders/{id}` | Retrieve an order by ID |
| PUT | `/api/orders/{id}` | Update an existing order |
| DELETE | `/api/orders/{id}` | Delete an order |

---

## Example API Request

### Create an Order

```http
POST /api/orders
Content-Type: application/json
```

```json
{
  "customerName": "Alice Johnson",
  "product": "Wireless Keyboard",
  "quantity": 2
}
```

### Example Response

```json
{
  "id": 1,
  "customerName": "Alice Johnson",
  "product": "Wireless Keyboard",
  "quantity": 2,
  "status": "PENDING",
  "createdAt": "2026-05-08T10:30:00"
}
```

---

## Swagger UI

Swagger UI is available after the application starts.

```text
http://localhost:8080/swagger-ui.html
```

Swagger UI provides interactive API documentation and allows the REST endpoints to be tested directly from the browser.

---

## Actuator Health Endpoints

The application exposes health endpoints through Spring Boot Actuator.

```text
http://localhost:8080/actuator/health
http://localhost:8080/actuator/health/liveness
http://localhost:8080/actuator/health/readiness
```

Expected response:

```json
{
  "status": "UP"
}
```

These endpoints are used by Kubernetes to determine whether the application is alive and ready to receive traffic.

---

## Running PostgreSQL with Docker

Start a local PostgreSQL container:

```bash
docker run --name postgres-local \
  -e POSTGRES_DB=orderdb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:16-alpine
```

Verify that PostgreSQL is running:

```bash
docker ps
```

---

## Application Configuration

Example `src/main/resources/application.properties` configuration:

```properties
spring.application.name=order-service

spring.datasource.url=jdbc:postgresql://localhost:5432/orderdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

management.endpoints.web.exposure.include=health,info
management.endpoint.health.probes.enabled=true
management.health.livenessstate.enabled=true
management.health.readinessstate.enabled=true
```

---

## Run the Application Locally

Compile the project:

```bash
./mvnw clean compile
```

Run the application:

```bash
./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

---

## Build and Run with Docker

Build the Docker image:

```bash
docker build -t order-service:latest .
```

Create a Docker network:

```bash
docker network create order-network
```

Connect PostgreSQL to the network:

```bash
docker network connect order-network postgres-local
```

Run the application container:

```bash
docker run -d \
  --name order-service \
  --network order-network \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-local:5432/orderdb \
  order-service:latest
```

The API will be available at:

```text
http://localhost:8080
```

---

## Kubernetes Deployment

Start Minikube:

```bash
minikube start
```

Load the Docker image into Minikube:

```bash
minikube image load order-service:latest
```

Apply the Kubernetes manifests:

```bash
kubectl apply -f k8s/
```

Check the running pods:

```bash
kubectl get pods
```

Check the services:

```bash
kubectl get services
```

Open the application through Minikube:

```bash
minikube service order-service
```

---

## Kubernetes Health Probes

The order service deployment uses Spring Boot Actuator endpoints for Kubernetes probes.

```text
/actuator/health/liveness
/actuator/health/readiness
```

The liveness probe checks whether the application is still running. If the application becomes unhealthy, Kubernetes can restart the pod.

The readiness probe checks whether the application is ready to receive traffic. Kubernetes only sends requests to pods that pass the readiness check.

---

## Rolling Updates

The Kubernetes deployment uses a rolling update strategy to reduce downtime during deployments.

```yaml
strategy:
  type: RollingUpdate
  rollingUpdate:
    maxUnavailable: 1
    maxSurge: 1
```

This allows Kubernetes to gradually replace old pods with new pods while keeping at least one healthy pod available to serve traffic.

---

## CI/CD Pipeline

GitHub Actions runs automatically on every push to the repository.

The CI pipeline:

1. Checks out the source code
2. Sets up Java
3. Compiles the Spring Boot application
4. Runs the test suite
5. Builds the Docker image
6. Starts the application container
7. Verifies the Actuator health endpoint
8. Reports whether the build passed or failed

Workflow file:

```text
.github/workflows/ci.yml
```

---

## Code Quality

This project includes Qodana for static code analysis.

Files:

```text
qodana.yaml
.github/workflows/qodana_code_quality.yml
```

Qodana helps detect code quality issues, maintainability problems, and potential bugs.

---

## Project Structure

```text
order-service
├── .github
│   └── workflows
│       ├── ci.yml
│       └── qodana_code_quality.yml
├── k8s
│   ├── order-service-deployment.yaml
│   └── postgres-deployment.yaml
├── src
│   ├── main
│   │   ├── java/com/example/orderservice
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   └── service
│   │   └── resources
│   │       └── application.properties
│   └── test
├── Dockerfile
├── pom.xml
├── qodana.yaml
└── README.md
```

---

## Key Learning Outcomes

Through this project, I practiced:

- Building REST APIs with Spring Boot
- Applying layered backend architecture
- Creating DTOs for request validation
- Handling validation and not-found errors cleanly
- Connecting Spring Boot to PostgreSQL
- Persisting data with Spring Data JPA
- Documenting APIs with Swagger UI
- Exposing operational health endpoints with Spring Boot Actuator
- Building a multi-stage Docker image
- Running containers with Docker networks
- Deploying applications to Kubernetes with Minikube
- Configuring Kubernetes liveness and readiness probes
- Understanding Kubernetes rolling update behavior
- Automating builds with GitHub Actions
- Managing code quality with Qodana
- Using Git and GitHub for version control

---

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

---

## Author

**Burale**  
GitHub: [ahmedburale](https://github.com/ahmedburale)

