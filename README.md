# 🛍️ Retail DDD Example

> *A showcase of how multiple teams can collaborate in a monorepo using Domain-Driven Design*

Welcome to our **Domain-Driven Design** showcase!
This isn't just another "Hello World" - it's a **multi-team monorepo** demonstrating how different bounded contexts can
coexist, evolve independently, and work together in harmony.
We've got hexagons, bounded contexts, and more patterns than a textile factory.

## 🎯 What's This All About?

This project demonstrates how **multiple development teams** can work effectively in a **single repository** using **DDD
principles**, **bounded contexts**, and **Hexagonal Architecture**.

**Key Concepts Demonstrated:**

- 🏢 **Multi-team ownership** - Each service represents a different team's domain
- 🔒 **Bounded contexts** - Clear domain boundaries with explicit interfaces
- 📦 **Monorepo benefits** - Shared tooling, unified CI/CD, cross-cutting concerns
- 🏗️ **Independent deployability** - Teams can deploy their services autonomously
- 📋 **Documentation-driven development** - ADRs, architectural guidelines, and decision tracking

Think of it as a **digital shopping mall** where each team owns their "store" (bounded context) but shares the
infrastructure, while maintaining clear ownership and autonomy.

## 🏬 The Domain: An online shop

The domain is a **retail e-commerce platform** with multiple bounded contexts,
each representing a different aspect of the business:

- **🛒 Shop Service**: The main attraction - handles articles, orders, and all that e-commerce jazz
- **🚚 Delivery Service**: Knows where your packages are (probably stuck in traffic)
- **📦 Warehouse Service**: Keeps track of inventory (yes, we're out of toilet paper again)

## 🎪 Tech Circus

Within this monorepo, we are using a variety of technologies to keep things interesting and modern.

**Backend Performers:**

- **Kotlin + Spring Boot** - Because who has time for Java verbosity?
- **PostgreSQL** - For when you need your data to actually persist
- **OAuth2 JWT** - Security that doesn't make you cry
- **ArchUnit** - The architecture police that never sleeps

**Frontend Stars:**

- **React 19 + TypeScript** - Modern, fast, and type-safe
- **Material-UI** - Pretty components without the CSS nightmares
- **TanStack Query** - State management that doesn't fight you

**Infrastructure Crew:**

- **Auth0** - Authentication that just works™
- **Docker** - Containerization for the container-curious
- **Kubernetes + Helm** - Orchestration without the orchestra pit
- **Minikube** - Kubernetes for your laptop

## 🚀 Quick Start (Or Not So Quick)

1. **Fire up the infrastructure** (grab a coffee, this takes a moment):
   ```bash
   minikube start
   kubectl config use-context minikube
   minikube tunnel # keep the terminal open
   ```
   
2. **Deploy database to minikube**:
   ```bash
   cd charts
   helm dependency build ./postgres
   helm upgrade --install postgres ./postgres
   kubectl get po -w # Wait until the database pod is ready
   ```

3. **Build everything** (grab another coffee):
   ```bash
   ./gradlew build
   ```

4. Start the services

   **a) locally**
   Run all provided configs within IntelliJ

   **b) within Minikube** (you know the drill):
   ```bash
   # Build all the images
   minikube image build -t shop-backend:local -f services/shop/shop-backend/Dockerfile .
   minikube image build -t delivery-backend:local -f services/delivery/delivery-backend/Dockerfile .
   minikube image build -t warehouse-backend:local -f services/warehouse/warehouse-backend/Dockerfile .
   minikube image build -t shop-frontend:local -f services/shop/shop-frontend/Dockerfile .
   
   # Deploy the shop
   helm upgrade --install shop-backend ./shop-backend --values ./shop-backend/values.local.yaml
   ```

## 🏗️ Architecture: It's Hexagonal, Darling

Each service follows the **Hexagonal Architecture** pattern because circles are overrated.
Here's how we keep our code clean and our sanity intact:

```
🏛️ Your Service Palace
├── 🚪 Adapters (The doormen)
│   ├── 📥 Inbound (REST controllers, the front desk)  
│   └── 📤 Outbound (Databases, external APIs, the back office)
├── 🧠 Application (The brain)
│   ├── 🔌 Ports (The contracts)
│   └── ⚙️ Services (The actual work happens here)
└── 💎 Domain (The precious business logic)
```

## 🧪 Testing: Because We're Not Savages

- **Unit Tests**: Test the tiny pieces
- **Integration Tests**: Test the bigger pieces
- **Architecture Tests**: Test that we didn't mess up the patterns
- **E2E Tests**: Test the whole shebang

Run all tests with: `./gradlew test` (and pray to the testing gods)

## 🤝 Contributing

Found a bug? Want to add a feature? Great! Just remember:

- Follow the hexagonal pattern (seriously, the ArchUnit tests will find you)
- Write tests (future you will thank present you)
- Keep the domain pure (no external dependencies in there!)

---

*Built with ❤️ by Miragon*