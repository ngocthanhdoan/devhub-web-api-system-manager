# Stage 1: Build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean install

# Stage 2: Package
FROM eclipse-temurin:21
WORKDIR /app
COPY  --from=build /app/target/devhub_web_api_system_manager-0.0.1-SNAPSHOT.jar devhub_web_api_system_manager.jar
EXPOSE 8080
CMD ["java", "-jar", "devhub_web_api_system_manager.jar"]