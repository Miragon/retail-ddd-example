FROM amazoncorretto:21-alpine

WORKDIR /app
RUN addgroup -S unprivileged && adduser -S unprivileged -G unprivileged
USER unprivileged

COPY services/hello-service/build/libs/*.jar app.jar
EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:+ExitOnOutOfMemoryError"
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]

