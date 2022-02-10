FROM openjdk:8-jdk-alpine
  
RUN mkdir app

COPY user-service/build/libs/api-gateway-service-another-1.0.0-RELEASE.jar ./app
COPY user-service/build/libs/user-service-1.0.0-RELEASE-plain.jar ./app

ENTRYPOINT ["java","-jar","/app/*.jar"]
