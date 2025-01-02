FROM openjdk:17-jdk
COPY target/PatientService-0.0.1-SNAPSHOT.jar GestionPatient.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s" , "-jar", "/GestionPatient.jar"]