FROM amazoncorretto:21-alpine
WORKDIR /app

COPY services/delivery/delivery-backend/build/libs/*.jar app.jar

EXPOSE 8080
USER 1000:1000

ENTRYPOINT ["java","-jar","/app/app.jar"]