FROM openjdk:8-jdk-alpine
  
RUN mkdir app

ARG JAR_DIR=build/libs/

COPY ${JAR_DIR} ./app

ENTRYPOINT ["java","-jar","/app/*.jar"]
