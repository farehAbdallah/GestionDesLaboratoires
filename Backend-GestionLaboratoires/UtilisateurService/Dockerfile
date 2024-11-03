# Dockerfile
FROM openjdk:17-jdk
COPY target/UtilisateurService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s" , "-jar", "/app.jar"]