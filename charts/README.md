# Local Kubernetes Setup with Minikube, Helm, Traefik and CNPG

Dieser Setup läuft komplett hinter Traefik. Zugriff erfolgt über:

- Frontend: `http://localhost:8080/`
- Shop Backend: `http://localhost:8080/api/...`
- Warehouse Backend: `http://localhost:8080/warehouse/...`
- Delivery Backend: `http://localhost:8080/delivery/...`

## Prerequisites

- Docker
- Minikube (docker driver)
- kubectl
- Helm

## 1) Minikube starten

```bash
minikube config set driver docker
minikube start -p retail-example
kubectl config use-context retail-example
```

## 2) Projekt bauen und Images in Minikube bauen

```bash
./gradlew build
npm --prefix services/shop/shop-frontend run build

minikube -p retail-example image build -t shop-backend:local -f services/shop/shop-backend/Dockerfile .
minikube -p retail-example image build -t delivery-backend:local -f services/delivery/delivery-backend/Dockerfile .
minikube -p retail-example image build -t warehouse-backend:local -f services/warehouse/warehouse-backend/Dockerfile .
minikube -p retail-example image build -t shop-frontend:local -f services/shop/shop-frontend/Dockerfile .
```

## 3) Infrastruktur deployen (CNPG Operator -> Postgres -> Traefik)

```bash
cd charts

helm dependency build ./infrastructure/cnpg-operator
helm upgrade --install cnpg ./infrastructure/cnpg-operator \
  --namespace cnpg-operator \
  --create-namespace \
  -f infrastructure/cnpg-operator/values.yaml

kubectl create namespace retail-local
kubectl apply -f local_secrets/postgres-secret.yaml

helm upgrade --install postgres ./infrastructure/postgres \
  --namespace retail-local \
  -f infrastructure/postgres/values.yaml 

helm dependency build ./infrastructure/traefik
helm upgrade --install traefik ./infrastructure/traefik \
  --namespace traefik \
  --create-namespace \
  -f infrastructure/traefik/values.yaml 
```

## 4) Services deployen

```bash
helm upgrade --install shop-backend ./shop-backend \
  --namespace retail-local \
  -f ./shop-backend/values.local.yaml

helm upgrade --install delivery-backend ./delivery-backend \
  --namespace retail-local \
  -f ./delivery-backend/values.local.yaml

helm upgrade --install warehouse-backend ./warehouse-backend \
  --namespace retail-local \
  -f ./warehouse-backend/values.local.yaml

helm upgrade --install shop-frontend ./shop-frontend \
  --namespace retail-local \
  -f ./shop-frontend/values.local.yaml
```

## 5) Zugriff aktivieren

Traefik läuft als `LoadBalancer` auf Port `8080` (Services routen intern per Traefik IngressRoute):

```bash
minikube tunnel -p retail-example
```

## 6) Routing testen

```bash
curl http://localhost:8080/
curl http://localhost:8080/api/articles
curl http://localhost:8080/warehouse/api/articles
curl http://localhost:8080/delivery/api/articles
```

## Nützliche Befehle

```bash
minikube list profiles
kubectl get pods -A
kubectl get svc -A
kubectl get ingressroute -n retail-local
kubectl get middleware -n retail-local
```
