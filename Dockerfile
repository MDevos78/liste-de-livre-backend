# Étape 1 : Le "Build Stage"
# Nous utilisons une image Maven et JDK 17 pour compiler votre code.
FROM maven:3.8.7-openjdk-17 AS build

# Copiez l'intégralité du contenu de votre projet (le ".") dans le conteneur.
# C'est la méthode la plus sûre pour éviter les erreurs de chemin.
COPY . .

# Exécutez le build Maven, en sautant les tests.
# La commande est maintenant lancée depuis la racine du projet dans le conteneur.
RUN mvn clean install

---

# Étape 2 : Le "Final Image Stage"
# Nous utilisons une image JDK 17 plus légère pour exécuter l'application.
FROM openjdk:17-jdk-slim

# Copiez le fichier .jar du "Build Stage" vers l'image finale.
COPY --from=build /target/liste-de-livre-backend-0.0.1-SNAPSHOT.jar app.jar

# Exposez le port sur lequel l'application s'exécute.
EXPOSE 8080

# Définissez le point d'entrée pour lancer l'application.
ENTRYPOINT ["java", "-jar", "app.jar"]