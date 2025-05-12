# Fullstack Example

Build project using Gradle and build Docker images for each service.
``` bash
make build-images
```

Run docker images locally:
``` bash
make run
```

Run image on local Kubernetes cluster:
``` bash
make deploy
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