FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Ensure the script has LF line endings
RUN sed -i 's/\r$//' ./mvnw && chmod +x ./mvnw
# Use the correct command to resolve dependencies
CMD ["./mvnw", "dependency:resolve"]
COPY src ./src

# Consolidate build and test stages
FROM base as build
# Build the application
CMD ["./mvnw", "package"]

# Define a "test" stage
FROM base as test
CMD ["./mvnw", "test"]

# Production stage
FROM eclipse-temurin:17-jdk-jammy as production
EXPOSE 9090
# Copy the built JAR file into the image
COPY target/TestingAndCi-0.0.1-SNAPSHOT.jar /TestingAndCi-0.0.1-SNAPSHOT.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/TestingAndCi-0.0.1-SNAPSHOT.jar"]
