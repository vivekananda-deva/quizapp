# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies separately from source for faster rebuilds
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=build /app/target/quizapp.jar app.jar

# Render provides PORT at runtime; 8080 is just a sane local default
ENV PORT=8080
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
