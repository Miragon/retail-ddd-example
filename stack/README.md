# Stack

Branch-isolierte Dev-Infrastruktur: pro Checkout läuft ein eigener
Postgres + Keycloak. Routing zu lokal gestarteten Backend-/Frontend-Prozessen
übernimmt [portless](http://github.com/vercel-labs/portless).

## Start

```bash
npm --prefix scripts install        # einmalig pro Checkout: installiert portless
npm --prefix scripts run dev        # Stack hoch + alle Backends/Frontend via portless
npm --prefix scripts run dev:down   # Compose stoppen (portless-Prozesse via Ctrl-C in dev)
```

`scripts/dev-env.sh` leitet aus dem aktuellen Git-Branch einen Slug ab und
berechnet daraus deterministische Host-Ports für Postgres und Keycloak.
Zwei Checkouts auf verschiedenen Branches laufen so kollisionsfrei parallel.

## Einstiegspunkte

`$BRANCH_SLUG` ergibt sich aus `git rev-parse --abbrev-ref HEAD` (Kleinbuchstaben,
`/` und `_` durch `-` ersetzt — z.B. `spike-keycloak-portless`).

| URL                                                | Ziel                         |
|----------------------------------------------------|------------------------------|
| `http://app.${BRANCH_SLUG}.localhost:1355`             | Frontend (Vite)              |
| `http://shop.${BRANCH_SLUG}.localhost:1355`            | shop-backend                 |
| `http://delivery.${BRANCH_SLUG}.localhost:1355`        | delivery-backend             |
| `http://warehouse.${BRANCH_SLUG}.localhost:1355`       | warehouse-backend            |
| `http://auth.${BRANCH_SLUG}.localhost:1355`            | Keycloak                     |
| `http://auth.${BRANCH_SLUG}.localhost/admin`      | Keycloak Admin (admin/admin) |

Postgres ist nicht über portless erreichbar (kein HTTP) — Verbindung direkt
auf `localhost:${POSTGRES_PORT}` (siehe `dev-env.sh`-Ausgabe beim Start).

## Testuser (Realm: retail)

| User | Passwort | Rollen |
|------|----------|--------|
| alice | test | CUSTOMER |
| bob | test | CUSTOMER |
| shopkeeper | test | CUSTOMER, ADMIN |

## Verzeichnisstruktur

```
stack/
├── keycloak/       # Realm-Konfiguration (keycloak-config-cli)
└── docker-compose.yml
```

## Klassischer Solo-Mode (ohne portless)

Wer nicht parallel arbeitet, kann weiterhin direkt mit dem dev-Profil starten:

```bash
SPRING_PROFILES_ACTIVE=dev ./gradlew :services:shop:shop-backend:bootRun
```

Dann gelten die fixen Ports aus `application-dev.yml` (shop 8081, delivery 8082,
warehouse 8083, mcp 8085) und der Vite-Proxy-Default zeigt auf `localhost:8081`.
Keycloak und Postgres müssen dafür separat aufgesetzt werden.
