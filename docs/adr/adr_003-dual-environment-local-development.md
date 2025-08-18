# ADR 0003: Use Dual Environment Approach for Local Development

## 📌 Status

Accepted

## 🧭 Context

Local development environments need to balance developer experience, production parity, and setup complexity. We need to provide database and infrastructure services for local development while ensuring our deployment configurations work correctly in production-like environments.

Developers have varying levels of experience with container orchestration platforms like Kubernetes. Most are familiar with Docker Compose for local development, while others prefer working directly with Kubernetes configurations.

Our production deployment targets Kubernetes, so we need to validate that our Helm charts work correctly. However, forcing all developers to use Kubernetes for local development would create friction and slow down the development process.

## ✅ Decision

We will provide **two parallel approaches** for local development:

### 1. Docker Compose (Primary Local Development)

- Use `docker-compose.yml` for spinning up infrastructure services (PostgreSQL, etc.)
- Run application services directly via Gradle (`./gradlew bootRun`)
- Optimized for fast development cycles and familiar developer workflows

### 2. Kubernetes/Helm (Production Parity Testing)

- Use Minikube with Helm charts for full production-like environment
- Build Docker images and deploy via Helm charts
- Required for validating deployment configurations before production

**Implementation Details:**

- Docker Compose files provide lightweight infrastructure setup
- Helm charts remain the single source of truth for deployment configuration
- Application services can run in either environment without code changes
- Environment-specific configuration handled via Spring profiles and environment variables

## 🔄 Alternatives Considered

### Single Kubernetes-Only Approach

**Pros:**
- Complete production parity
- Single deployment method to maintain
- Forces developers to understand Kubernetes

**Cons:**
- High barrier to entry for developers unfamiliar with Kubernetes
- Slower development cycles due to image building and pod startup times
- Complex setup requirements (Minikube, kubectl, Helm)
- Database access more complex for local development tools

### Single Docker Compose-Only Approach

**Pros:**
- Familiar to most developers
- Fast local development cycles
- Simple infrastructure setup

**Cons:**
- No validation of Kubernetes deployment configurations
- Potential for environment-specific bugs in production
- Drift between local and production environments

## 🎯 Consequences

**Positive:**
- Developers can choose their preferred local development approach
- Fast development cycles with Docker Compose
- Production deployment validation with Kubernetes/Helm
- Lower barrier to entry for new developers
- Flexibility to evolve either approach independently

**Negative:**
- Maintenance overhead for two development approaches
- Potential for configuration drift between environments
- Documentation complexity for explaining both approaches
- Need to ensure feature parity between both setups

**Mitigation Strategies:**
- Automate validation that both environments work correctly
- Use environment variables and Spring profiles for configuration differences
- Document clear guidelines on when to use each approach
- Regular testing of Helm deployments in CI/CD pipeline