FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/synthetic-human-core-starter-3.4.4.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
