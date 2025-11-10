FROM eclipse-temurin:24-jdk AS build

RUN microdnf install -y findutils || apt-get update && apt-get install -y findutils

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY gradlew .
COPY gradle gradle

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

FROM eclipse-temurin:24-jdk
VOLUME /tmp

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]