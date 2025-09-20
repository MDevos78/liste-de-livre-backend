# Étape 1 : Le "Build Stage"
FROM maven:3.8.7-openjdk-17 AS build

# Définition du répertoire de travail dans le conteneur
WORKDIR /usr/src/app

# Copie des fichiers de projet nécessaires
COPY pom.xml .
COPY code ./code

# Exécution du build Maven
RUN mvn clean install -DskipTests

---

# Étape 2 : Le "Final Image Stage"
FROM openjdk:17-jdk-slim

# Copie du fichier .jar du "Build Stage" vers l'image finale
COPY --from=build /usr/src/app/target/liste-de-livre-backend-0.0.1-SNAPSHOT.jar /app.jar

# Exposition du port
EXPOSE 8080

# Commande de démarrage de l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]