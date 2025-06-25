FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY todoApp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]