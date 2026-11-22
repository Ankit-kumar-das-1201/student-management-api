# Student Management API

A RESTful Student Management API built with **Java, Spring Boot, Spring Data JPA, and PostgreSQL**. The application exposes endpoints to create, retrieve, update, delete, search, and filter student records. It also includes request validation, centralized error responses, API documentation, and repository integration tests using PostgreSQL Testcontainers.

**Repository:** https://github.com/Ankit-kumar-das-1201/student-management-api

---

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Application Architecture](#application-architecture)
- [Project Structure](#project-structure)
- [API Base URL](#api-base-url)
- [API Endpoints](#api-endpoints)
- [Request and Response Examples](#request-and-response-examples)
- [Error Handling](#error-handling)
- [Database Configuration](#database-configuration)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [API Documentation with Swagger](#api-documentation-with-swagger)
- [Testing](#testing)
- [Future Improvements](#future-improvements)

---

## Features

- Create a student record.
- Retrieve a student by ID.
- Retrieve all students using pagination and sorting.
- Update an existing student.
- Delete a student.
- Search students by name.
- Find students by email.
- Filter students by age using derived Spring Data JPA queries.
- Filter students by age using a native SQL query.
- Validate incoming request data using Jakarta Bean Validation.
- Return structured error responses through centralized exception handling.
- Generate interactive API documentation using Swagger UI / OpenAPI.
- Test repository and database integration using PostgreSQL Testcontainers.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language (as configured in the current GitHub `pom.xml`) |
| Spring Boot 3.5.15 | Application framework (as configured in the current GitHub `pom.xml`) |
| Spring Web | REST API and HTTP request handling |
| Spring Data JPA | Repository and persistence layer |
| Hibernate | JPA implementation / ORM |
| PostgreSQL | Relational database |
| Jakarta Bean Validation | Request validation |
| Lombok | Boilerplate-code reduction |
| Springdoc OpenAPI 2.8.9 | OpenAPI documentation and Swagger UI |
| Maven | Build and dependency management |
| JUnit 5 and Spring Boot Test | Automated testing |
| Testcontainers | PostgreSQL container for integration tests |
| H2 | Test-scope database dependency present in the Maven configuration |

> The versions above reflect the `pom.xml` currently available in the linked GitHub repository. Keep this section in sync if you change the Java or Spring Boot version.

## Application Architecture

The application follows a layered architecture. Requests are handled by the controller, business operations are delegated to the service layer, and data access is handled by Spring Data JPA repositories.

```text
Client (Postman / Browser)
          |
          v
   StudentController
          |
          v
    StudentService
          |
          v
  StudentRepository
          |
          v
      PostgreSQL
```

Supporting components include:

- **Request DTO:** receives incoming student data (`StudentRequest`).
- **Response DTO:** exposes student data to API clients (`StudentResponse`).
- **Exception handling:** converts application exceptions into a consistent error response (`ErrorResponse`).

## Project Structure

The code shared for this project uses the following package organization. The tree below shows the main application components; add or adjust entries if your local source tree contains additional classes.

```text
student-management-api/
├── src/
│   ├── main/
│   │   ├── java/com/ankit/studentmanagement/
│   │   │   ├── StudentManagementApiApplication.java
│   │   │   ├── controller/
│   │   │   │   └── StudentController.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   └── StudentRequest.java
│   │   │   │   └── response/
│   │   │   │       ├── StudentResponse.java
│   │   │   │       └── ErrorResponse.java
│   │   │   ├── entity/
│   │   │   │   └── Student.java
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/ankit/studentmanagement/
│           └── repository/
│               └── StudentRepositoryTest.java
├── .mvn/
│   └── wrapper/
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## API Base URL

When running locally:

```text
http://localhost:8080/api/students
```

## API Endpoints

The following endpoints are defined in the current `StudentController` shared for this README.

| HTTP Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/students` | Retrieve students using pagination and sorting. |
| `GET` | `/api/students/{id}` | Retrieve a student by ID. |
| `POST` | `/api/students` | Create a student. Returns `201 Created`. |
| `PUT` | `/api/students/{id}` | Update a student. |
| `DELETE` | `/api/students/{id}` | Delete a student. Returns `204 No Content`. |
| `GET` | `/api/students/search?name={name}` | Search students by name. |
| `GET` | `/api/students/filter/older-than?age={age}` | Find students older than the supplied age using a repository query. |
| `GET` | `/api/students/filter/native/older-than?age={age}` | Find students older than the supplied age using a native query. |
| `GET` | `/api/students/filter/ByEmail?email={email}` | Find a student by email. |
| `PUT` | `/api/students/test/{id}` | Calls the student-name update service operation. This is currently exposed under a `test` path. |

### Pagination and sorting

The `GET /api/students` endpoint accepts Spring Data `Pageable` parameters. For example:

```http
GET http://localhost:8080/api/students?page=0&size=10&sort=name,asc
```

- `page`: zero-based page index.
- `size`: number of records per page.
- `sort`: property and direction, such as `name,asc` or `age,desc`.

### Filtering examples

```http
GET http://localhost:8080/api/students/search?name=Ankit
GET http://localhost:8080/api/students/filter/older-than?age=18
GET http://localhost:8080/api/students/filter/native/older-than?age=18
GET http://localhost:8080/api/students/filter/ByEmail?email=ankit@example.com
```

> **API design note:** `/api/students/test/{id}` appears to be a development/test-style route. Consider replacing it with a meaningful resource route or removing it before treating the API as production-ready. The email route currently uses the exact path casing `/filter/ByEmail`; clients should use that spelling unless the mapping is changed.

## Request and Response Examples

The examples below use the fields visible in the `StudentResponse` record: `id`, `age`, `name`, and `email`. The create/update request examples assume the corresponding `StudentRequest` accepts `age`, `name`, and `email`; confirm its validation rules in the DTO before relying on the examples.

### Create a student

```http
POST /api/students
Content-Type: application/json
```

```json
{
  "name": "Ankit Kumar Das",
  "email": "ankit@example.com",
  "age": 21
}
```

The controller returns `201 Created` with a `StudentResponse` body. Example:

```json
{
  "id": 1,
  "age": 21,
  "name": "Ankit Kumar Das",
  "email": "ankit@example.com"
}
```

The `id` value is generated by the persistence layer; the example value is illustrative.

### Get a student by ID

```http
GET /api/students/1
```

Example response body:

```json
{
  "id": 1,
  "age": 21,
  "name": "Ankit Kumar Das",
  "email": "ankit@example.com"
}
```

### Update a student

```http
PUT /api/students/1
Content-Type: application/json
```

```json
{
  "name": "Ankit Das",
  "email": "ankit@example.com",
  "age": 22
}
```

The controller returns the updated `StudentResponse`.

### Delete a student

```http
DELETE /api/students/1
```

A successful delete returns `204 No Content` with no response body.

### Find a student by email

```http
GET /api/students/filter/ByEmail?email=ankit@example.com
```

Example response body:

```json
{
  "id": 1,
  "age": 21,
  "name": "Ankit Kumar Das",
  "email": "ankit@example.com"
}
```

### Error response

The application defines an `ErrorResponse` record with the following fields:

| Field | Description |
|---|---|
| `timestamp` | Date and time when the error was generated. |
| `status` | HTTP status code. |
| `error` | HTTP reason phrase. |
| `message` | Error message. |
| `errors` | List of additional validation or error details. |
| `path` | Request URI that caused the error. |

Example shape for a not-found response:

```json
{
  "timestamp": "2026-11-22T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with email: ankit@example.com",
  "errors": [],
  "path": "/api/students/filter/ByEmail"
}
```

The timestamp, message, and path are illustrative. The actual message is determined by the custom exception, and the exact response depends on the request and exception handler.

## Error Handling

The project uses custom runtime exceptions and a global exception handler to produce structured API error responses rather than exposing raw stack traces to API clients.

The `ErrorResponse` DTO contains:

- Timestamp
- HTTP status code
- HTTP reason phrase
- Error message
- Additional error details
- Request path

The email lookup operation throws a custom `StudentNotFoundWithEmail` exception when no student is found. Its global handler maps that exception to `404 Not Found`.

## Database Configuration

The application uses PostgreSQL. The local database configuration shared for this project uses the database name `student_management` and the PostgreSQL user `postgres`.

Create the database before starting the application if it does not already exist:

```sql
CREATE DATABASE student_management;
```

Configure the connection in `src/main/resources/application.properties`. Prefer environment variables for credentials instead of committing a password:

```properties
spring.application.name=student-management-api

spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/student_management}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update
```

Set `DB_PASSWORD` in your environment before running the application. You can also set `DB_URL` and `DB_USERNAME` if your local database uses different values.

> `spring.jpa.hibernate.ddl-auto=update` is convenient for local development. For production deployments, use a deliberate schema migration strategy and review database credentials and access controls.

## Getting Started

### Prerequisites

- Java 21 (matching the current GitHub `pom.xml`).
- PostgreSQL installed and running, or a reachable PostgreSQL instance.
- Docker Desktop or another Docker-compatible environment for running Testcontainers tests.
- Git (to clone the repository).

Maven Wrapper scripts are included in the repository, so a separate Maven installation is not required for the commands below.

### 1. Clone the repository

```bash
git clone https://github.com/Ankit-kumar-das-1201/student-management-api.git
cd student-management-api
```

### 2. Create and configure the database

Create a PostgreSQL database named `student_management`, then configure the datasource properties as described in [Database Configuration](#database-configuration).

### 3. Set the database password

In PowerShell, for the current terminal session:

```powershell
$env:DB_PASSWORD = "your_local_postgres_password"
```

On macOS/Linux:

```bash
export DB_PASSWORD="your_local_postgres_password"
```

Do not replace the placeholder with a real password in this README or commit credentials to Git.

## Running the Application

From the project root, run the Maven Wrapper command for your operating system.

**Windows PowerShell:**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**

```bash
./mvnw spring-boot:run
```

The API should then be available at:

```text
http://localhost:8080/api/students
```

## API Documentation with Swagger

The project includes `springdoc-openapi-starter-webmvc-ui`. With the application running, open:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Use Swagger UI to inspect the available operations and try requests against your local application.

## Testing

The project includes Spring Boot testing dependencies and Testcontainers dependencies for PostgreSQL integration tests.

### Run tests

**Windows PowerShell:**

```powershell
.\mvnw.cmd test
```

**macOS / Linux:**

```bash
./mvnw test
```

### Testcontainers setup

The repository tests use Testcontainers to start a disposable PostgreSQL database for integration testing. Make sure Docker is running before executing tests that use Testcontainers.

The test configuration uses:

- `@DataJpaTest` to configure a JPA-focused test context.
- `@Testcontainers` to integrate Testcontainers with JUnit 5.
- `@Container` to manage the PostgreSQL container lifecycle.
- `@ServiceConnection` to provide the container's connection details to Spring Boot.
- `@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)` to prevent the test slice from replacing the PostgreSQL datasource with an embedded database.

The repository tests developed for this project cover database connectivity and student repository operations, including email lookup for an existing and a non-existing student, as well as the previously implemented name and age queries.

## Future Improvements

Potential improvements for a future iteration include:

- Add API examples and automated contract tests for every endpoint.
- Add a dedicated route for the student-name update operation instead of `/test/{id}`.
- Add database schema migrations (for example, Flyway) instead of relying on `ddl-auto=update`.
- Add authentication and authorization if the API requires protected access.
- Add CI workflow to run the test suite automatically on pushes and pull requests.
- Add deployment configuration and production observability.

---

## Author

**Ankit Kumar Das**  
GitHub: https://github.com/Ankit-kumar-das-1201

---

If you find an issue or have a suggestion, feel free to open an issue in the [GitHub repository](https://github.com/Ankit-kumar-das-1201/student-management-api).
