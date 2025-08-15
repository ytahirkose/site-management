# Java 21 base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy Gradle wrapper
COPY gradlew .
COPY gradle gradle

# Copy source code and build files
COPY src src
COPY build.gradle settings.gradle ./

# Make gradlew executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar -x test --no-daemon

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/site-management-1.0.0.jar"]
