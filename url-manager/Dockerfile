# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine
VOLUME /tmp
WORKDIR /app

# Copy the packaged application from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Set default environment variables (these can be overridden by Docker/Kubernetes)
ENV SPRING_DATA_MONGODB_URI=your_default_mongodb_uri
ENV KEY_STORE=your_default_keystore_password

# Set the active Spring profile
ENV SPRING_PROFILES_ACTIVE=k8s

# Expose the application port (as defined in your properties, here 9000)
EXPOSE 9000

# Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]
