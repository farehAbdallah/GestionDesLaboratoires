FROM openjdk:17-jdk

# Copier le fichier JAR de l'application
COPY target/GesionAnalyse-1.0-SNAPSHOT.jar GestionAnalyse.jar

# Copier les fichiers de configuration dans l'image Docker
COPY src/main/resources/application-k8s.properties /resources/application-k8s.properties
COPY src/main/resources/application.properties /resources/application.properties

# Configurer le profil par défaut pour Kubernetes
ENV SPRING_PROFILES_ACTIVE=k8s

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/GestionAnalyse.jar"]
