# ADR 0002: Adopt Hexagonal Architecture for Backend Services

## 📌 Status

Accepted

## 🧭 Context

To build maintainable, testable, and loosely coupled backend services,
we aim to separate concerns clearly and enforce strict architectural boundaries.
Hexagonal Architecture (also known as Ports and Adapters) supports this by isolating
the domain logic from infrastructure and external systems.
It also improves our ability to evolve, test, and reason about our code.

## ✅ Decision

We will structure our backend services following the principles of Hexagonal Architecture
with the following directory layout:

```
📦 src
┣ 📂 adapter
┃ ┣ 📂 inbound # e.g., REST controllers, GraphQL resolvers
┃ ┗ 📂 outbound # e.g., database access, external APIs
┣ 📂 application
┃ ┣ 📂 port
┃ ┃ ┣ 📂 inbound # use-case interfaces (e.g., commands/queries)
┃ ┃ ┗ 📂 outbound # interfaces to external systems
┃ ┗ 📂 service # application services implementing in-ports and using out-ports
┗ 📂 domain # pure business logic, technology-agnostic
```

**Architectural Rules:**

- Domain has no dependencies on other layers
- Application layer depends only on domain
- Adapters implement ports but don't depend on each other

To ensure adherence to this architectural pattern, we will write **ArchUnit** tests that:

- Enforce package/module boundaries
- Prevent dependencies from crossing architectural layers in the wrong direction
- Ensure adapters implement or call the correct interfaces only

## 🎯 Consequences

Positive:

- Clear and enforced separation of concerns across backend services
- Core domain logic remains decoupled from infrastructure and framework code
- Easier onboarding for new developers due to consistent structure
- High testability through isolation of business logic
- Flexibility to swap infrastructure implementations

Negative:

- Initial setup effort and learning curve for the team
- Additional abstraction layers may introduce complexity for simple operations
