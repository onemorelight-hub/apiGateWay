# For Java 8, try this
FROM openjdk:8-jdk-alpine

# For Java 11, try this
#FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=target/apiGateWay-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-api-gateway.jar /opt/app/apiGateWay-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} apiGateWay-0.0.1-SNAPSHOT.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","apiGateWay-0.0.1-SNAPSHOT.jar"]
