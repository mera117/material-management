# Material Management API

API REST desarrollada con Spring Boot para la gestión de materiales, departamentos y ciudades.

## Tecnologías

* Java 21
* Spring Boot 3.5
* Spring Data JPA
* PostgreSQL
* Maven
* Swagger OpenAPI

## Arquitectura

El proyecto sigue una arquitectura por capas:

```text
controller
service
repository
entity
dto
exception
config
```

### Componentes

* Controllers: Exponen los endpoints REST.
* Services: Contienen la lógica de negocio.
* Repositories: Acceso a datos mediante Spring Data JPA.
* DTOs: Objetos de transferencia de información.
* Exception Handler: Manejo centralizado de errores.
* Config: Configuraciones generales (Swagger, CORS, etc.).

## Funcionalidades

### Materiales

* Crear material
* Actualizar material
* Consultar todos los materiales
* Buscar materiales por tipo
* Buscar materiales por ciudad
* Buscar materiales por fecha de compra

### Catálogos

* Consultar departamentos
* Consultar ciudades
* Consultar ciudades por departamento
* Consultar tipos de material
* Consultar estados de material

## Reglas de Negocio

* La fecha de compra no puede ser mayor a la fecha de venta.
* Los materiales deben estar asociados a una ciudad válida.
* El tipo y estado del material son obligatorios.

## Base de Datos

Motor utilizado:

```text
PostgreSQL
```

Base de datos:

```text
material_management
```

## Configuración

application.yml

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/material_management
    username: postgres
    password: postgres

  jpa:
    hibernate:
      ddl-auto: update
```

## Ejecución

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en:

```text
http://localhost:8081
```

## Swagger

```text
http://localhost:8081/swagger-ui/index.html
```

## Autor

Elizabeth Rodríguez
Senior Java Developer

```
```
