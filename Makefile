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
.PHONY: all build dockerize run check-jar check-image check-java

all: check-java build dockerize run

build: check-java
	@if [ -n "$(JAVA_HOME_FOR_VERSION)" ]; then \
		echo "Building with Java $(EXPECTED_JAVA_MAJOR) (JAVA_HOME=$(JAVA_HOME_FOR_VERSION))"; \
		JAVA_HOME=$(JAVA_HOME_FOR_VERSION) $(GRADLE_CMD) clean bootJar; \
	else \
		echo "Building with current system Java (no JAVA_HOME override)"; \
		$(GRADLE_CMD) clean bootJar; \
	fi

check-jar:
	@if ls $(JAR_FILE) 1> /dev/null 2>&1; then \
		echo "JAR file found."; \
	else \
		echo "No JAR file found. Running build..."; \
		$(MAKE) build; \
	fi

check-image:
	@if docker image inspect $(IMAGE_NAME) > /dev/null 2>&1; then \
		echo "Docker image '$(IMAGE_NAME)' already exists."; \
	else \
		echo "Docker image '$(IMAGE_NAME)' not found. Building..."; \
		$(MAKE) dockerize; \
	fi

dockerize: check-jar
	docker build -t $(IMAGE_NAME) services/hello-service

run: check-image
	docker run -p 8080:8080 $(IMAGE_NAME)

check-java:
	@echo "Checking Java version (expected: $(EXPECTED_JAVA_MAJOR))..."
	@JAVA_VERSION=$$(java -version 2>&1 | awk -F[\".] '/version/ {print $$2}'); \
	if [ "$$JAVA_VERSION" -ne $(EXPECTED_JAVA_MAJOR) ]; then \
		if [ -n "$(JAVA_HOME_FOR_VERSION)" ]; then \
			echo "Current Java version ($$JAVA_VERSION) does not match, but a correct Java $(EXPECTED_JAVA_MAJOR) installation was found."; \
		else \
			echo "Wrong Java version detected: $$JAVA_VERSION"; \
			echo "Expected Java $(EXPECTED_JAVA_MAJOR)."; \
			echo "No matching JVM found automatically."; \
			echo "Build may fail. Please install Java $(EXPECTED_JAVA_MAJOR)."; \
			exit 1; \
		fi \
	else \
		echo "Correct Java version detected: $$JAVA_VERSION"; \
	fi
