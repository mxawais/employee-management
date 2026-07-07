# Use Eclipse Temurin Java 21 Runtime
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR
COPY target/employee-management-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
