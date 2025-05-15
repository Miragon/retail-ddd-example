GRADLE_CMD := ./gradlew
EXPECTED_JAVA_MAJOR := $(shell cat .java-version)
SDKMAN_DIR := $(HOME)/.sdkman

all: help

.PHONY: help
help:	## Show this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m\033[0m\n"} /^[a-zA-Z_.-]+:.*?##/ { printf "  \033[36m%-38s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

.PHONY: setup-java
setup-java: ## Setup the development environment.
ifdef CUSTOM_JAVA_EXECUTABLE
	$(eval JAVA_EXECUTABLE := $(CUSTOM_JAVA_EXECUTABLE))
else ifneq ($(wildcard $(SDKMAN_DIR)),)
	$(eval SDKMAN_JAVA_VERSION := $(shell source "$(SDKMAN_DIR)/bin/sdkman-init.sh" && sdk list java | grep "installed" | grep ${EXPECTED_JAVA_MAJOR} | head -1 | awk '{print $$NF}' 2>/dev/null || echo ""))
	@echo "Using SDKMAN Java version: $(SDKMAN_JAVA_VERSION)"
	$(eval JAVA_EXECUTABLE := $(if $(SDKMAN_JAVA_VERSION),$(shell source "$(SDKMAN_DIR)/bin/sdkman-init.sh" && sdk home java $(SDKMAN_JAVA_VERSION) 2>/dev/null || echo ""),))
else
	$(eval JAVA_EXECUTABLE := $(shell which java 2>/dev/null || echo ""))
endif
	@echo "Using Java executable: $(JAVA_EXECUTABLE)"

.PHONY: verify-setup
verify-setup: setup-java ## Verify the setup of Java, Docker, Kubernetes, and Helm.
	@if [ -z "$(JAVA_EXECUTABLE)" ]; then \
		echo "ERROR: java is not installed."; \
		exit 1; \
	fi
	java_major_version=$$($(JAVA_EXECUTABLE) --version 2>&1 | awk 'NR == 1 { split($$2, ver, "."); print ver[1] }'); \
	if [ "$$java_major_version" != "$(EXPECTED_JAVA_MAJOR)" ]; then \
		echo "ERROR: Make sure to use Java version $(EXPECTED_JAVA_MAJOR). Found: $$java_major_version"; \
		exit 1; \
	else \
		echo "Java version $$java_major_version matches expected version $(EXPECTED_JAVA_MAJOR)."; \
	fi; \
	if ! command -v docker >/dev/null 2>&1; then \
		echo "ERROR: Docker is not installed or not in PATH."; \
		exit 1; \
		else \
		echo "Docker is installed."; \
	fi
	if ! command -v helm >/dev/null 2>&1; then \
		echo "ERROR: Helm is not installed or not in PATH."; \
		exit 1; \
		else \
		echo "Helm is installed."; \
	fi

.PHONY: build
build: setup-java build-images ## Build the project and create Docker images.

.PHONY: build-project
build-project: verify-setup ## Build the project.
	JAVA_HOME=$(shell dirname $(shell dirname $(JAVA_EXECUTABLE))) $(GRADLE_CMD) clean bootJar

.PHONY: build-images
build-images: build-project ## Create Docker images.
	docker build -t hello-service:local services/hello-service

.PHONY: run
run: build-images ## Run the Docker images.
	docker run -p 8080:8080 hello-service:local

.PHONY: deploy
deploy: build-images ## Deploy the service to Kubernetes.
	helm install hello-service ./charts/hello-service
	@echo ""
	@echo "-------------------------------------------------------------"
	@echo "The hello-service is now available at: http://localhost:30080/hello"
	@echo "Make sure to wait until the service is healthy"
	@echo "To uninstall the service, run: helm uninstall hello-service"
	@echo "-------------------------------------------------------------"

#######################
# Helpers			  #
#######################

# TODO fixme - we should switch to minikube and check if we are connected to the minikube cluster
.PHONY: check-kubernetes-local
check-kubernetes-local: ## Check if Kubernetes is running locally.
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
