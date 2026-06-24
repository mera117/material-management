# Material Management

Solución Full Stack para la gestión de materiales desarrollada como prueba técnica.

## Descripción

La aplicación permite administrar materiales mediante operaciones de consulta, registro y actualización.

La solución está compuesta por:

```text
material-management
└── Backend Spring Boot

material-management-ui
└── Frontend Angular
```

## Arquitectura General

```text
Angular 20
      |
      |
REST API
      |
      |
Spring Boot 3.5
      |
      |
PostgreSQL
```

## Componentes

### Backend

Tecnologías:

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Swagger

Funcionalidades:

* CRUD de materiales
* Gestión de departamentos
* Gestión de ciudades
* Filtros por tipo
* Filtros por ciudad
* Filtros por fecha de compra
* Validaciones de negocio
* Manejo global de excepciones

### Frontend

Tecnologías:

* Angular 20
* TypeScript
* SCSS

Funcionalidades:

* Registro de materiales
* Edición de materiales
* Consulta de materiales
* Filtros dinámicos
* Catálogos dependientes

## Estructura del Repositorio

```text
material-management/

├── material-management
│   ├── src
│   ├── pom.xml
│   └── README.md
│
├── material-management-ui
│   ├── src
│   ├── package.json
│   └── README.md
│
└── README.md
```

## Ejecución

### Backend

```bash
cd material-management

mvn clean install

mvn spring-boot:run
```

Puerto:

```text
8081
```

Swagger:

```text
http://localhost:8081/swagger-ui/index.html
```

### Frontend

```bash
cd material-management-ui

npm install

ng serve --port 4201
```

Puerto:

```text
4201
```

Aplicación:

```text
http://localhost:4201
```

## Consideraciones

* Se implementó arquitectura por capas.
* Se utilizaron DTOs para entrada y salida.
* Se implementó manejo global de excepciones.
* Se aplicaron validaciones de negocio.
* Se utilizó PostgreSQL como motor de persistencia.
* La solución cumple los requerimientos funcionales planteados en la prueba técnica.

## Autor

Elizabeth Rodríguez
Senior Java Developer

```
```
