# Local Kubernetes Setup with Minikube & Helm

This setup mirrors our cloud environment using Helm charts and Minikube.
Configurations are always versioned with the chart.

## Prerequisites

- Docker
- Minikube (with docker driver)
- kubectl
- Helm

## Start Minikube

```bash
# You may need to set a specific driver
minikube config set driver docker

# Start Minikube with the Docker driver
minikube start
```

## Build Images inside Minikube

Before deploying, you need to build the project

```bash
# Build the Gradle project
cd ..
./gradlew build
```

After that, you can build the Docker images directly in Minikube:

```bash
cd ..

# Build backend images
minikube image build -t shop-backend:local -f services/shop/shop-backend/Dockerfile .
minikube image build -t delivery-backend:local -f services/delivery/delivery-backend/Dockerfile .
minikube image build -t warehouse-backend:local -f services/warehouse/warehouse-backend/Dockerfile .

# Build frontend image
minikube image build -t shop-frontend:local -f services/shop/shop-frontend/Dockerfile .
```

Moreover you may need to build the Postgres image:

```bash
cd postgres
helm dependency build
```

## Deploy with Helm

To install all charts at once:

```bash
# Install infrastructure first
helm upgrade --install postgres ./postgres

# Install all backend applications
helm upgrade --install shop-backend ./shop-backend --values ./shop-backend/values.local.yaml
helm upgrade --install delivery-backend ./delivery-backend --values ./delivery-backend/values.local.yaml
helm upgrade --install warehouse-backend ./warehouse-backend --values ./warehouse-backend/values.local.yaml

# Install frontend 
helm upgrade --install shop-frontend ./shop-frontend --values ./shop-frontend/values.local.yaml
```

## Access Services

1. Start tunnel (for LoadBalancer services):

```bash
minikube tunnel
```

## 🔍 Useful Commands

```bash
kubectl get pods
kubectl logs -f <pod-name>
minikube dashboard
```
