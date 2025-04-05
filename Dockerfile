# -------------------------------------------
# Etapa 1: Construcción (Build)
# -------------------------------------------
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiamos Maven Wrapper (si lo usas), pom.xml, etc.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Descargar dependencias offline
RUN ./mvnw dependency:go-offline

# Copiamos el código fuente y compilamos el proyecto
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# -------------------------------------------
# Etapa 2: Imagen de Ejecución (Runtime)
# -------------------------------------------
FROM openjdk:17-slim
WORKDIR /app

# Copia el jar generado en la etapa "build"
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto donde corre Spring Boot (8080 por defecto)
EXPOSE 8080

# Iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
