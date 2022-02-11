FROM openjdk:11-slim

RUN mkdir app

ARG JAR_DIR=user-service/build/libs/

COPY ${JAR_DIR} ./app

ENTRYPOINT ["java","-jar","./app/api-gateway-service-1.0.0-RELEASE.jar"]
