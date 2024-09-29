FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY ./pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8
EXPOSE 8080
COPY  --from=build /home/app/target/authentication-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]