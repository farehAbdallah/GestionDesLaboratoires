FROM openjdk:17-jdk
COPY target/LaboratoireService-1.0-SNAPSHOT.jar GestionLaboratoire.jar
ENTRYPOINT ["java","-Dspring.profiles.active=k8s", "-jar", "/GestionLaboratoire.jar"]
