# Detect OS
UNAME_S := $(shell uname -s)

# Default Gradle command
GRADLE_CMD := ./gradlew
ifeq ($(OS),Windows_NT)
    GRADLE_CMD := gradlew.bat
endif

# Read expected Java version from gradle/libs.versions.toml
EXPECTED_JAVA_MAJOR := $(shell grep '^java' gradle/libs.versions.toml | sed 's/[^0-9]*\([0-9]*\).*/\1/')

# Try to find JAVA_HOME for the expected version (macOS way)
JAVA_HOME_FOR_VERSION := $(shell /usr/libexec/java_home -v $(EXPECTED_JAVA_MAJOR) 2>/dev/null || echo "")

# Paths and names
JAR_FILE := services/hello-service/build/libs/*.jar
IMAGE_NAME := hello-service:local

# Targets
.PHONY: build-images build-project dockerize run check-jar check-image \
        check-java-available check-docker-available check-kubernetes-local \
        check-helm deploy

#######################
# Build targets       #
#######################

build-images: check-java-available check-docker-available build dockerize

build-project: check-java-available
	JAVA_HOME=$(JAVA_HOME_FOR_VERSION) $(GRADLE_CMD) clean bootJar

dockerize: check-jar check-docker-available
	docker build -t $(IMAGE_NAME) services/hello-service

run: check-image check-docker-available
	docker run -p 8080:8080 $(IMAGE_NAME)

deploy: check-image check-kubernetes-local check-helm
	helm install hello-service ./charts/hello-service
	@echo ""
	@echo "-------------------------------------------------------------"
	@echo "The hello-service is now available at: http://localhost:30080/hello"
	@echo "Make sure to wait until the service is healthy"
	@echo "To uninstall the service, run: helm uninstall hello-service"
	@echo "-------------------------------------------------------------"

#######################
# Environment checks  #
#######################

check-jar: check-java-available
	@if ls $(JAR_FILE) 1> /dev/null 2>&1; then \
		echo "JAR file found."; \
	else \
		echo "No JAR file found. Running build..."; \
		$(MAKE) build; \
	fi

check-image: check-docker-available
	@if docker image inspect $(IMAGE_NAME) > /dev/null 2>&1; then \
		echo "Docker image '$(IMAGE_NAME)' already exists."; \
	else \
		echo "Docker image '$(IMAGE_NAME)' not found. Building..."; \
		$(MAKE) dockerize; \
	fi


check-java-available:
	@if [ -z "$(JAVA_HOME_FOR_VERSION)" ]; then \
		echo "ERROR: Java $(EXPECTED_JAVA_MAJOR) not found on this machine."; \
		echo "Please install the correct Java version."; \
		exit 1; \
	else \
		echo "Using Java $(EXPECTED_JAVA_MAJOR) at $(JAVA_HOME_FOR_VERSION)"; \
	fi

check-docker-available:
	@if ! command -v docker >/dev/null 2>&1; then \
		echo "ERROR: Docker is not installed or not in PATH."; \
		exit 1; \
	fi
	@if ! docker info >/dev/null 2>&1; then \
		echo "ERROR: Docker daemon is not running."; \
		exit 1; \
	fi
	@echo "Docker is available and running."

check-kubernetes-local:
	@if ! command -v kubectl >/dev/null 2>&1; then \
		echo "ERROR: kubectl is not installed or not in PATH."; \
		exit 1; \
	fi
	@if ! kubectl cluster-info >/dev/null 2>&1; then \
		echo "ERROR: Cannot connect to a Kubernetes cluster."; \
		exit 1; \
	fi
	@context=$$(kubectl config current-context); \
	echo "Current context: $$context"; \
	if [ "$$context" != "docker-desktop" ] && [ "$$context" != "minikube" ]; then \
		echo "WARNING: Current Kubernetes context is not a known local one."; \
		echo "Activate docker-desktop locally: https://docs.docker.com/desktop/features/kubernetes/"; \
		echo "To switch to Docker Desktop, run:"; \
		echo "  kubectl config get-contexts"; \
		echo "  kubectl config use-context docker-desktop"; \
	else \
		echo "Kubernetes is connected to a local cluster ($$context)."; \
	fi

check-helm:
	@if ! command -v helm >/dev/null 2>&1; then \
		echo "ERROR: Helm is not installed or not in PATH."; \
		exit 1; \
	fi
	@echo "Helm is available."
