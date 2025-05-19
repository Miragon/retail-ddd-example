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
minikube start
```

## Build Images inside Minikube

```bash
cd ..
minikube image build -t hello-service:local -f services/hello-service/Dockerfile .
```

## Deploy with Helm

### Infrastructure
```bash
helm upgrade --install postgres ./postgres
```

### Applications
```bash
helm upgrade --install hello-service ./hello-service --values ./hello-service/values.local.yaml
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
