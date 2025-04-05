# Registro de Tareas para Tutores (RETU)

## 1. Descripción del Proyecto

**Registro de Tareas para Tutores (RETU)** es una aplicación que permite:
- Gestionar un listado de **tutores** (nombre, correo, materia, contraseña).
- Gestionar un listado de **tareas** (título, descripción, fecha de entrega, categoría).
- Realizar un **login** por correo y contraseña para acceder a un **dashboard** (home) donde se pueden consultar datos de tutores y tareas.
- Generar un PDF de la lista de tareas.
- Manejar contraseñas en texto plano con `{noop}`, para fines de demostración (facilita la prueba del login).



## 2. Tecnologías Usadas y Versiones

- **Java 17**
- **Spring Boot 3.3.10**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Thymeleaf
- **PostgreSQL 14** (vía Docker)
- **Maven 3.8.4** (para la etapa de compilación en Dockerfile)
- **Docker & Docker Compose** (para la contenedorización)
- **iText 5.5.13.3** (para generación de PDF)

---

## 3. Cómo Levantar el Proyecto

### 3.1. Requisitos Previos

1. **Git** (opcional, para clonar el repositorio).
2. **Docker** y **Docker Compose** instalados.
3. Puerto **8080** libre en tu máquina (para la app) y puerto **5432** (para PostgreSQL).

### 3.2. Pasos

1. **Clonar** este repositorio (o descargar el código):

   ```bash
   git clone https://github.com/srnjdev/RETU.git
   cd RETU
   ```

## 4. Construir y arrancar los contenedores con Docker Compose:

   ```bash
   docker compose up --build
   ```

Esto:
1. Compilará el proyecto en una imagen Docker (etapa Maven).
2. Levantará la base de datos PostgreSQL.
3. Creará e iniciará el contenedor de la aplicación Spring Boot.

## 5. Acceder a la aplicación:
- Navega a http://localhost:8080/login para el login.
- Tras iniciar sesión, te redirigirá a /home (dashboard).

### 5.1. Otras Rutas de Interés
- **Tutores:** http://localhost:8080/tutores
- **Tareas:** http://localhost:8080/tareas

## 6. Usuario de Prueba (Seeder)
La base de datos crea automáticamente (la primera vez que se levanta el contenedor) un usuario de prueba a partir de un script SQL. En la tabla tutor, se inserta el siguiente registro:

- **Correo:** admin@ues.edu.sv
- **Contraseña:** admin

(Almacenada en la base como "{noop}admin" para que Spring Security la reconozca como texto plano.)

Para loguearte en el sistema:
- **Correo / Usuario:** admin@ues.edu.sv
- **Contraseña:** admin