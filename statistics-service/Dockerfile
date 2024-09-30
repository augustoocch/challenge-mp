# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM adoptopenjdk/openjdk17:jre-17.0.0_35-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s", "-jar", "app.jar"]
