FROM openjdk:17-jdk
COPY target/GestionAnalyse-1.0-SNAPSHOT.jar GestionAnalyse.jar
ENTRYPOINT ["java","-Dspring.profiles.active=k8s", "-jar", "/GestionAnalyse.jar"]
