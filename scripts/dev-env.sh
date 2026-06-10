#!/usr/bin/env bash
# Common environment for the portless-based parallel dev setup.
# Sourced by dev.sh and dev-down.sh — derives branch-aware values from the
# current git branch so that two checkouts running this script never collide.

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

BRANCH_SLUG="$(git -C "$REPO_ROOT" rev-parse --abbrev-ref HEAD | tr '/_' '--' | tr '[:upper:]' '[:lower:]')"
HASH="$(echo -n "$BRANCH_SLUG" | cksum | cut -d ' ' -f1)"

export BRANCH_SLUG
export POSTGRES_PORT=$((15000 + HASH % 1000))
export KEYCLOAK_PORT=$((16000 + HASH % 1000))
export COMPOSE_PROJECT_NAME="retail-${BRANCH_SLUG}"

export PORTLESS_HTTPS=0
export PORTLESS_PORT=1355
PORTLESS_BASE_SUFFIX=".${BRANCH_SLUG}.localhost:${PORTLESS_PORT}"

export ISSUER_URI="http://auth${PORTLESS_BASE_SUFFIX}/realms/retail"
export SHOP_BACKEND_URL="http://shop${PORTLESS_BASE_SUFFIX}"

export DATABASE_URL="jdbc:postgresql://localhost:${POSTGRES_PORT}/test"
export DATABASE_USER=test
export DATABASE_PASSWORD=test
export DATABASE_DRIVER_CLASS_NAME=org.postgresql.Driver
export DATABASE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
export DATABASE_SAMPLE_DATA_ENABLED=true

export CORS_ALLOWED_ORIGINS="http://app${PORTLESS_BASE_SUFFIX}"
export SWAGGER_API_ENABLED=true

# Disable the dev profile so that application-dev.yml's fixed port does NOT
# override portless's ephemeral PORT assignment.
unset SPRING_PROFILES_ACTIVE
