GRADLE_CMD := ./gradlew
EXPECTED_JAVA_MAJOR := $(shell cat .java-version)
SDKMAN_DIR := $(HOME)/.sdkman

all: help

.PHONY: help
help:	## Show this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m\033[0m\n"} /^[a-zA-Z_.-]+:.*?##/ { printf "  \033[36m%-38s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

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
deploy: check-kubernetes-local        ## Deploy the service to Kubernetes.
	@set -e; \
	context=$$(kubectl config current-context); \
	if [ "$$context" = "minikube" ]; then \
		local_id=$$(docker image inspect --format='{{.Id}}' hello-service:local 2>/dev/null || true); \
		remote_id=$$(minikube ssh -- docker image inspect --format='{{.Id}}' hello-service:local 2>/dev/null || true); \
		if [ -z "$$local_id" ]; then \
			echo "ERROR: hello-service:local is not built locally."; \
			exit 1; \
		fi; \
		echo "Local   image ID : $$local_id"; \
		echo "Minikube image ID: $${remote_id:-<not present>}"; \
		if [ "$$local_id" !== "$$remote_id" ]; then \
			echo "Loading hello-service:local into Minikube…"; \
			minikube image load hello-service:local; \
		else \
			echo "Image already up-to-date in Minikube — skipping load."; \
		fi; \
	fi; \
	helm upgrade --install hello-service ./charts/hello-service

#######################
# Helpers			  #
#######################

.PHONY: setup-java
setup-java:
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
	@# Check Java version
	@if [ -z "$(JAVA_EXECUTABLE)" ]; then \
		echo "ERROR: java is not installed."; \
		exit 1; \
	fi
	@java_major_version=$$($(JAVA_EXECUTABLE) --version 2>/dev/null | awk 'NR == 1 { split($$2, ver, "."); print ver[1] }'); \
	if [ "$$java_major_version" != "$(EXPECTED_JAVA_MAJOR)" ]; then \
		echo "ERROR: Make sure to use Java version $(EXPECTED_JAVA_MAJOR). Found: $$java_major_version"; \
		echo "	Switch version with SDKMAN or set CUSTOM_JAVA_EXECUTABLE to the correct Java. F.ex. using direnv:"; \
		echo "	echo 'export CUSTOM_JAVA_EXECUTABLE=~/Library/Java/JavaVirtualMachines/corretto-21.0.7/Contents/Home/bin/java' > .envrc && direnv allow ." ; \
		exit 1; \
	else \
		echo "Java version $$java_major_version matches expected version $(EXPECTED_JAVA_MAJOR)."; \
	fi
	@# Check Docker
	@if ! command -v docker >/dev/null 2>&1; then \
		echo "ERROR: Docker is not installed or not in PATH."; \
		exit 1; \
	else \
		echo "Docker is installed."; \
	fi
	@# Check Helm
	@if ! command -v helm >/dev/null 2>&1; then \
		echo "ERROR: Helm is not installed or not in PATH."; \
		exit 1; \
	else \
		echo "Helm is installed."; \
	fi


.PHONY: check-kubernetes-local
check-kubernetes-local: ## Check if Kubernetes is running locally.
	@set -e; \
	if ! command -v kubectl >/dev/null 2>&1; then \
		echo "ERROR: kubectl is not installed or not in PATH."; \
		exit 1; \
	fi; \
	if ! kubectl cluster-info >/dev/null 2>&1; then \
		echo "ERROR: Cannot connect to a Kubernetes cluster."; \
		exit 1; \
	fi; \
	context=$$(kubectl config current-context); \
	server=$$(kubectl config view --minify -o jsonpath='{.clusters[0].cluster.server}'); \
	echo "Current context : $$context"; \
	echo "API server      : $$server"; \
	case "$$server" in *127.0.0.1*|*localhost*|*0.0.0.0*|*192.168.*|*172.1[6-9].*|*172.2[0-9].*|*172.3[0-1].*) \
		echo "✔ Detected a local cluster."; ;; *) \
		if echo "$$context" | grep -Eq '^(docker-desktop|minikube|kind-|k3d-|microk8s|rancher-desktop)'; then \
			echo "✔ Detected a local cluster (context name '\''$$context'\'')."; \
		else \
			echo "x Cluster does not look local."; \
			echo "  To switch to a local context, run:"; \
			echo "    kubectl config get-contexts"; \
			echo "    kubectl config use-context <local-context>"; \
		fi; \
	esac
