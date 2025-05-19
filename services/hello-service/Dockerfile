# syntax=docker/dockerfile:1.7 # enables cache-mounts
FROM amazoncorretto:21-alpine AS builder
WORKDIR /src

# ---- tools the wrapper needs ------------------------------------------
RUN apk add --no-cache bash curl git zip unzip

# ---- copy build scripts first (max layer-cache) -----------------------
COPY gradlew gradle.properties settings.gradle.kts ./
COPY gradle/ gradle
COPY buildSrc/ buildSrc

# ---- download the wrapper & plugin jars once, keep them in cache ------
RUN --mount=type=cache,target=/root/.gradle ./gradlew --no-daemon help

# ---- now copy only the service sources --------------------------------
COPY services/hello-service/ services/hello-service

# ---- compile + package, re-using the same cache -----------------------
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon --parallel --build-cache :services:hello-service:bootJar

####################  RUNTIME  ####################
FROM amazoncorretto:21-alpine
WORKDIR /app
RUN addgroup -S unprivileged && adduser -S unprivileged -G unprivileged
USER unprivileged

COPY --from=builder /src/services/hello-service/build/libs/*.jar app.jar
EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:+ExitOnOutOfMemoryError"
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]
