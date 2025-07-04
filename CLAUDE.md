# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build System & Development

This is a multi-module Gradle project using Kotlin and Spring Boot. Java 21 is required.

### Common Commands

**Build entire project:**

```bash
./gradlew build
```

**Run tests:**

```bash
./gradlew test
```

**Run specific service locally:**

```bash
./gradlew :services:shop:shop-backend:bootRun
./gradlew :services:delivery:delivery-backend:bootRun
./gradlew :services:warehouse:warehouse-backend:bootRun
```

**Run single test:**

```bash
./gradlew :services:shop:shop-backend:test --tests "LoadArticlesServiceTest"
```

**Build Docker images:**

```bash
docker build -t shop-backend:local -f services/shop/shop-backend/Dockerfile .
```

### Frontend Development

The shop frontend is a React + Vite application:

**Frontend commands (in services/shop/shop-frontend/):**

```bash
npm run dev          # Start development server
npm run build        # Build for production  
npm run test:eslint  # Run ESLint
npm run apiGeneration # Generate API client from OpenAPI spec
```

**E2E Testing (in services/shop/shop-e2e/):**

```bash
npm run cy:all    # Run all Cypress tests
npm run cy:spec   # Run specific test spec
```

## Architecture Overview

This project implements Domain-Driven Design (DDD) with Hexagonal Architecture across multiple bounded contexts:

### Services Structure

- **shop-backend**: Main e-commerce service with full CRUD, persistence, and security
- **delivery-backend**: Delivery domain service (in-memory storage)
- **warehouse-backend**: Warehouse domain service (in-memory storage)
- **shop-frontend**: React SPA with Material-UI
- **shop-e2e**: Cypress end-to-end tests

### Hexagonal Architecture Pattern

Each backend service follows strict hexagonal architecture enforced by ArchUnit tests:

```
src/main/kotlin/
├── adapter/
│   ├── inbound/     # REST controllers, security config
│   └── outbound/    # JPA repositories, external API clients
├── application/
│   ├── port/
│   │   ├── inbound/  # UseCase/Query interfaces
│   │   └── outbound/ # Repository interfaces
│   └── service/     # Application services implementing use cases
└── domain/          # Pure business logic, no dependencies
```

**Architectural Rules:**

- Domain layer has no external dependencies
- Application layer depends only on domain
- Adapters implement ports but don't depend on each other
- Use cases end with "UseCase" or "Query"
- Application services end with "Service"
- Each service implements exactly one inbound port

### Technology Stack

**Backend:**

- Kotlin + Spring Boot 3.5.3
- Spring Security with OAuth2 JWT
- Spring Data JPA with PostgreSQL
- SpringDoc OpenAPI
- ArchUnit for architecture testing

**Frontend:**

- React 19 + TypeScript
- Vite build tool
- Material-UI components
- TanStack Query for state management
- Auth0 for authentication

**Infrastructure:**

- Docker containers
- Kubernetes/Helm for deployment
- Minikube for local development

## Domain Model

The core domain revolves around Articles with shared value objects:

- `ArticleId`, `ArticleName`, `ArticleDescription`, `Price`, `ImageUrl`

Each service implements its own article repository following the same domain model but different persistence strategies.

## Local Development Setup

1. **Start infrastructure:**

```bash
cd charts
minikube start
helm upgrade --install postgres ./postgres
```

2. **Build and deploy services:**

**Build images in minikube:**

```bash
minikube image build -t shop-backend:local -f services/shop/shop-backend/Dockerfile .
```

```bash
minikube image build -t delivery-backend:local -f services/delivery/delivery-backend/Dockerfile .
```

```bash
minikube image build -t warehouse-backend:local -f services/warehouse/warehouse-backend/Dockerfile .
```

```bash
minikube image build -t shop-frontend:local -f services/shop/shop-frontend/Dockerfile .
```

**Deploy with Helm:**

```bash
helm upgrade --install shop-backend ./shop-backend --values ./shop-backend/values.local.yaml
```

3. **Access services:**

```bash
minikube tunnel  # Enable LoadBalancer access
```

## Testing Strategy

- **Unit tests**: Service layer and domain logic
- **Integration tests**: Repository layer with H2 database
- **Architecture tests**: ArchUnit enforces hexagonal boundaries
- **E2E tests**: Cypress for full user workflows

Always run `./gradlew test` before committing to ensure architecture compliance and functionality.

## Implementation Guidelines

Always use IDE diagnostics to validate code after implementation
