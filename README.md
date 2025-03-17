# Electric House Merchandise Catalog System

## Introduction
The Electric House Merchandise Catalog System is a web application designed to manage the catalog of products available in an electrical store. The system does not handle stock levels but provides a structured way to organize and display available merchandise.

## Technologies Used
- **Backend:** Java with Spring Boot
- **Frontend:** Thymeleaf (Server-Side Rendering)
- **Database:** MySQL with JPA/Hibernate for ORM
- **Security:** Spring Security for authentication and role management
- **Build Tool:** Maven
- **Version Control:** Git/GitHub
- **Dependency Management:** Lombok (to reduce boilerplate code)

## Project Structure
```
project-root/
│── src/
│   ├── main/
│   │   ├── java/com/electrichouse/
│   │   │   ├── controllers/  # Controllers handling HTTP requests
│   │   │   ├── entities/     # Entity classes for ORM mapping
│   │   │   ├── repositories/ # JPA repositories for data access
│   │   │   ├── services/     # Business logic and service layer
│   │   ├── resources/
│   │   │   ├── templates/    # Thymeleaf HTML templates
│   │   │   ├── static/       # CSS, JS, images
│   │   │   ├── application.properties # Configuration settings
│── pom.xml                   # Maven build file
│── README.md                 # Project documentation
```

## Core Features
- **User Management:**
  - Two roles: `ADMIN` and `USER`.
  - Admin is assigned directly in the database.
  - Users register with `USER` role by default.
- **Catalog Management:**
  - Create, update, and list articles.
  - Associate articles with manufacturers.
  - Display a structured product catalog.
- **Authentication & Security:**
  - Password encryption.
  - Role-based access control.

## Future Enhancements
- Role-based restrictions (only admins can manage articles)
- Image uploads for articles
- Article preview with images
- User profile editing

---

# Sistema de Catálogo de Mercadería para Casa de Electricidad

## Introducción
El Sistema de Catálogo de Mercadería para Casa de Electricidad es una aplicación web diseñada para gestionar el catálogo de productos disponibles en una tienda de electricidad. No maneja niveles de stock, sino que organiza y muestra los productos de manera estructurada.

## Tecnologías Utilizadas
- **Backend:** Java con Spring Boot
- **Frontend:** Thymeleaf (Renderizado en el servidor)
- **Base de Datos:** MySQL con JPA/Hibernate para ORM
- **Seguridad:** Spring Security para autenticación y gestión de roles
- **Herramienta de Construcción:** Maven
- **Control de Versiones:** Git/GitHub
- **Gestión de Dependencias:** Lombok (para reducir código repetitivo)

## Estructura del Proyecto
```
project-root/
│── src/
│   ├── main/
│   │   ├── java/com/electrichouse/
│   │   │   ├── controllers/  # Controladores para manejar solicitudes HTTP
│   │   │   ├── entities/     # Clases de entidad para mapeo ORM
│   │   │   ├── repositories/ # Repositorios JPA para acceso a datos
│   │   │   ├── services/     # Capa de servicios y lógica de negocio
│   │   ├── resources/
│   │   │   ├── templates/    # Plantillas HTML de Thymeleaf
│   │   │   ├── static/       # Archivos CSS, JS, imágenes
│   │   │   ├── application.properties # Configuración
│── pom.xml                   # Archivo de construcción Maven
│── README.md                 # Documentación del proyecto
```

## Funcionalidades Principales
- **Gestión de Usuarios:**
  - Dos roles: `ADMIN` y `USER`.
  - El administrador se asigna directamente en la base de datos.
  - Los usuarios se registran con el rol `USER` por defecto.
- **Gestión del Catálogo:**
  - Crear, actualizar y listar artículos.
  - Asociar artículos con fabricantes.
  - Mostrar un catálogo estructurado de productos.
- **Autenticación y Seguridad:**
  - Encriptación de contraseñas.
  - Control de acceso basado en roles.

## Mejoras Futuras
- Restricción de permisos (solo administradores pueden gestionar artículos)
- Carga de imágenes para artículos
- Vista previa de artículos con imágenes
- Edición de perfil de usuario
