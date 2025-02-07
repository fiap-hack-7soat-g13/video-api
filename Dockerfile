FROM maven:3-eclipse-temurin-23-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD [ "java", "-jar", "app.jar" ]
