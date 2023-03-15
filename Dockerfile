FROM openjdk:17-slim-buster

EXPOSE 8080

WORKDIR /app

COPY ./src/main/resources ./src/main/resources
COPY ./build/libs/core-service.jar .

CMD ["java", "-jar", "./core-service.jar"]