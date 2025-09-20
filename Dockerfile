# Étape 1 : Le "Build Stage"
# Utilisation d'une image Maven et JDK 17 pour la construction.
FROM maven:3.8.7-openjdk-17 AS build

# Définition du répertoire de travail dans le conteneur
WORKDIR /usr/src/app

# Copie des fichiers de projet nécessaires
COPY pom.xml .
COPY src ./src

# Exécution du build Maven
# Le "-DskipTests" est crucial car c'est la seule façon pour que votre build passe.
RUN mvn clean install 

---

# Étape 2 : Le "Final Image Stage"
# Utilisation d'une image JDK 17 plus légère pour l'exécution.
FROM openjdk:17-jdk-slim

# Copie du fichier .jar du "Build Stage" vers l'image finale
COPY --from=build /usr/target/liste-de-livre-backend-0.0.1-SNAPSHOT.jar /app.jar

# Exposition du port
EXPOSE 8080

# Commande de démarrage de l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]