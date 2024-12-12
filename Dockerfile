# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/slotmachine-0.0.1-SNAPSHOT.jar slotmachine.jar

# Expose the application port
EXPOSE 8081

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "slotmachine.jar"]