# Use an official Gradle image with Amazon Corretto 17 JDK for the build stage
FROM gradle:8.1.1-jdk17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle files
COPY build.gradle settings.gradle ./
# Copy the source code into the container
COPY src ./src
#Copy modules
COPY momo ./momo

# Build the application (skipping tests if desired)
RUN gradle clean build -x test --no-daemon

# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the compiled source code from the build stage to the runtime stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port that the Spring Boot application runs on
EXPOSE 8080

# Command to run the application using Java
CMD ["java", "-jar", "app.jar"]
