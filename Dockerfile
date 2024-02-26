FROM alpine:3.18.4

# Mettre à jour les packages disponibles dans l'image Alpine
RUN apk update && apk add --no-cache openjdk17 maven

WORKDIR /app

COPY pom.xml .

# Copier tout le contenu du projet (y compris le code source) dans le répertoire de travail
COPY . .

# Exécuter Maven pour construire le projet (vous pouvez spécifier d'autres objectifs Maven si nécessaire)
RUN mvn clean package -DskipTests

# Exposer le port 8080 (si votre application Spring Boot écoute sur ce port)
EXPOSE 8080

# Commande par défaut pour exécuter l'application Spring Boot (ajuster en fonction de votre projet)
CMD ["java", "-jar", "target/saveursolidaire.jar"]