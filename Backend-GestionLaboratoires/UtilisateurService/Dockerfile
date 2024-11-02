FROM openjdk:17-jdk
COPY target/utilisateur-service-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s", "-jar", "/app.jar"]
