# PostgreSQL Stack

This directory contains PostgreSQL container configuration for local development.

## Installation

Docker Desktop should already be installed. If not:

```bash
brew install --cask docker
```

## Docker Compose

Start PostgreSQL with Docker:

```bash
docker-compose up -d
```

Stop and remove:

```bash
docker-compose down
```

## Database Connection

- **Host**: localhost
- **Port**: 5432
- **Database**: test
- **Username**: test
- **Password**: test

## Health Check

Verify PostgreSQL is ready:

```bash
docker exec retail-postgres pg_isready -d test -U test
```