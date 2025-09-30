FROM openjdk:21-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR compilado
COPY target/*.jar app.jar

# Exponer el puerto
EXPOSE 8081

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]