# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built jar file
COPY build/libs/pokemon-0.0.1-SNAPSHOT.jar app/pokemon-service.jar

# Expose port (change if needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app/pokemon-service.jar"]
