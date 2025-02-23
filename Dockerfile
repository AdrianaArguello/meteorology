FROM gradle:8.3-jdk11 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/meteorology-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
