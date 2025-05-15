# Fullstack Example

Use `make help` to see all available commands.

```bash
Usage:
  make 
  help                                    Show this help.
  setup-java                              Setup the development environment.
  verify-setup                            Verify the setup of Java, Docker, Kubernetes, and Helm.
  build                                   Build the project and create Docker images.
  build-project                           Build the project.
  build-images                            Create Docker images.
  run                                     Run the Docker images.
  deploy                                  Deploy the service to Kubernetes.
  check-kubernetes-local                  Check if Kubernetes is running locally.
```

## Container Engine: Docker
- Dockerfile for each service

## Build Tool: Gradle
- Gradle Wrapper to ensure consistent builds across environments
- Multi-module setup with `hello-service` and `utils` subprojects
- Convention plugin in `buildSrc` for shared build logic
- Version catalog for dependency management in `gradle/libs.versions.toml`

## IDE-Support: IntelliJ
- IntelliJ project files are included in the repository
- Run configurations for each service are provided
