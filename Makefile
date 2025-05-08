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
IMAGE_NAME := hello-service

# Targets
.PHONY: all build dockerize run check-jar check-image check-java-available check-docker-available

all: check-java-available check-docker-available build dockerize

build: check-java-available
	JAVA_HOME=$(JAVA_HOME_FOR_VERSION) $(GRADLE_CMD) clean bootJar

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

dockerize: check-jar check-docker-available
	docker build -t $(IMAGE_NAME) services/hello-service

run: check-image check-docker-available
	docker run -p 8080:8080 $(IMAGE_NAME)

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
