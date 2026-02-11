FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY castellarin-autorepuestos/pom.xml .
RUN mvn dependency:go-offline

COPY castellarin-autorepuestos/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

copy --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "--add-opens", "java.base/java.time=ALL-UNNAMED", "-jar", "app.jar"]