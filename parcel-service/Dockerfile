FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /workspace
COPY pom.xml .
COPY parcel-service/pom.xml parcel-service/pom.xml
COPY sensor-service/pom.xml sensor-service/pom.xml
RUN mvn -q -pl parcel-service -am dependency:go-offline
COPY parcel-service/src parcel-service/src
RUN mvn -q -pl parcel-service -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /workspace/parcel-service/target/parcel-service-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
