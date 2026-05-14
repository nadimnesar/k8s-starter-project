# Stage 1: Build
# Uses a Maven image with JDK 25 to compile the application
FROM maven:3.9-eclipse-temurin-25-alpine AS build
WORKDIR /app
# Copy only pom.xml first — Docker caches this layer so dependencies
# are only re-downloaded when pom.xml changes (not on every code change)
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B
# Now copy the source and compile
COPY src src
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests -B

# Stage 2: Runtime
# Uses a slim JRE image — much smaller than JDK, no compiler or build tools
FROM eclipse-temurin:25-jre-alpine AS runtime
WORKDIR /app
# Copy only the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
