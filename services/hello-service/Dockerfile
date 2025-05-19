# Build Stage
FROM amazoncorretto:21-alpine AS builder
WORKDIR /app

COPY ./gradle.properties ./
COPY ./settings.gradle.kts ./
COPY ./gradlew ./
COPY ./gradle ./gradle
COPY ./buildSrc ./buildSrc
COPY ./services ./services

RUN ./gradlew bootJar

# Production Stage
FROM amazoncorretto:21-alpine
WORKDIR /app

# Copy only the built JAR from the builder stage
COPY --from=builder /app/services/hello-service/build/libs/*.jar app.jar

EXPOSE 8080
USER 1000:1000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
