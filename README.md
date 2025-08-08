   <p align="left">
   <img src="https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green">
   </p>

# 📚 LiterAlura

Aplicación de consola desarrollada en **Java 24** con **Spring Boot**, orientada a la gestión y exploración de libros y autores. Permite interactuar con una base de datos de literatura a través de un menú intuitivo, desde el cual se pueden realizar búsquedas, listados, y más.

---

## 🎯 Objetivo

Este proyecto busca aplicar conceptos avanzados de Java y Spring Boot, integrando consumo de API, persistencia con PostgreSQL y estructuración limpia del código mediante DTOs, servicios y repositorios. Forma parte del aprendizaje basado en proyectos del curso de **Alura**.

---

## 🧩 Funcionalidades del Menú

📘 MENÚ PRINCIPAL 📘
Elija la opción a través de su número:

1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma (es/en)
6 - Listar 10 libros más descargados
7 - Buscar autor por nombre
0 - Salir

---

## 🧱 Estructura del Proyecto
```
src/
└── main/
├── java/
│ └── com.aluracursos.literatura/
│ ├── dto/ # Clases DTO para datos externos
│ │ ├── Datos.java
│ │ ├── DatosAutor.java
│ │ └── DatosLibros.java
│ ├── model/ # Entidades del modelo (JPA)
│ │ ├── Autor.java
│ │ └── Libro.java
│ ├── principal/ # Clase principal con el menú interactivo
│ │ └── Principal.java
│ ├── repository/ # Repositorios Spring JPA
│ ├── service/ # Lógica de negocio y API externa
│ │ ├── ConsumoAPI.java
│ │ ├── ConvierteDatos.java
│ │ └── IConvierteDatos.java
│ └── LiteraturaApplication.java # Clase main de Spring Boot
└── resources/
└── application.properties
```

## 🛠️ ## 🛠️ Requisitos del Sistema y Tecnologías Usadas

- 🟢 Java Runtime Environment (JRE) 17 o superior
- 💻 Sistema Operativo Windows, macOS o Linux
- 📚 IntelliJ IDEA
- ✅ Java SDK 24
- ✅ Spring Boot
- ✅ PostgreSQL
- ✅ Jackson (manejo de JSON)
- ✅ Lombok (anotaciones para simplificar código)
- ✅ Maven (gestión de dependencias)

---

## 📦 Instalación y Configuracion

1. 📥 Descarga el archivo zip en tu computadora
2. 🔍 Asegúrate de tener Java v24 instalado en tu sistema
3. 💾 Descarga y actualiza el archivo Pom.xml
4. 🚀 Abre y ejecuta el archivo con IntelliJ IDEA

Configura la conexión a PostgreSQL:

Edita el archivo application.properties con tus datos:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

Verás el menú principal para consultar libros y autores.

🌐 Consumo de API
La aplicación puede conectarse a una API pública (como Open Library) para obtener información de libros y autores y almacenarla en la base de datos.

🧪 Ejemplo de uso

```
Ingrese una opción:
1
Ingrese el título del libro:
Don Quijote

📚 Libro encontrado:
Título: Don Quijote
Autor: Miguel de Cervantes
Idioma: es
Año de publicación: 1605

```
📌 Notas
Las clases en dto reciben y transforman la información proveniente de la API.

El paquete service contiene la lógica para consumir la API, transformar datos y gestionar persistencia.

Se usa Spring Data JPA para las operaciones de base de datos, simplificando queries.

👤 Autor
Desarrollado por William Jose Gallo Marquez
🎓 Proyecto educativo - Curso de Java + Spring de Alura

