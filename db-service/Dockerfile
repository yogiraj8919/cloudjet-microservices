FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y docker.io

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]