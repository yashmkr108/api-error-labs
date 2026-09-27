# API Error Lab

A Spring Boot REST API project focused on designing production-oriented
exception handling and API error responses.

This project explores how validation errors, business exceptions, and
unexpected server failures should be handled and exposed through a
consistent HTTP API.

The goal is not just to learn Spring annotations, but to understand the
boundary between application/business logic and HTTP error handling.

---

##  Project Goals

This project was built to understand:

- How Spring handles validation failures
- Global exception handling with `@RestControllerAdvice`
- `@ExceptionHandler`
- Custom business exceptions
- Exception-to-HTTP status mapping
- Structured API error responses
- Field-level and object-level validation errors
- Generic exception handling
- Preventing internal implementation details from leaking to clients
- Separation of business logic from HTTP concerns

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Validation
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- Lombok

---

#  Architecture

The project follows a layered architecture:

```text
HTTP Request
     │
     ▼
Controller
     │
     ▼
Validation
     │
     ├────────────── Invalid ──────────────► 400
     │
     ▼
Service Layer
     │
     ├── Resource not found ──────────────► 404
     │
     ├── Business conflict ───────────────► 409
     │
     └── Unexpected failure ──────────────► 500
                         │
                         ▼
              Global Exception Handler
                         │
                         ▼
                  Structured API Error
````

The service layer is responsible for application and business rules.

The exception handling layer translates those application errors into
HTTP responses.

---

#  Exception Handling

Global exception handling is implemented using:

```java
@RestControllerAdvice
```

Specific exceptions are handled using:

```java
@ExceptionHandler
```

Example flow:

```text
UserNotFoundException
        │
        ▼
GlobalExceptionHandler
        │
        ▼
HTTP 404
```

Business exceptions do not return `ResponseEntity` directly from the
service layer.

Instead:

```text
Service
   │
   └── throws application exception
              │
              ▼
       Global Handler
              │
              ▼
          HTTP response
```

This keeps business logic independent from HTTP concerns.

---

# ✅ Validation Handling

Bean Validation is used for request validation.

Example:

```java
@NotBlank
@Email
String email;
```

When validation fails, Spring raises:

```text
MethodArgumentNotValidException
```

The global handler extracts the validation errors and converts them into
a structured API response.

---

## Field-Level Validation

Field validation errors are represented separately.

Example:

```json
{
  "fieldErrors": {
    "email": [
      "must be a well-formed email address"
    ],
    "age": [
      "must be greater than or equal to 0"
    ]
  }
}
```

Multiple validation errors for the same field are supported.

```json
{
  "fieldErrors": {
    "password": [
      "must not be blank",
      "size must be between 8 and 50"
    ]
  }
}
```

---

## Object-Level Validation

Cross-field validation is handled separately.

For example:

```java
@PasswordsMatch
public record UserRequest(...) {
}
```

Since this validation involves multiple fields, it produces an
object-level validation error.

These errors are represented as:

```json
{
  "globalErrors": [
    "Password do not match"
  ]
}
```

This keeps field-specific errors separate from validation rules that
apply to the entire request object.

---

#  Business Exceptions

Business rules are represented using custom exceptions.

Example:

```java
throw new EmailAlreadyExistsException(email);
```

The service layer expresses the business problem without knowing anything
about HTTP.

The global exception handler maps it to the appropriate HTTP response.

Example:

```text
EmailAlreadyExistsException
            │
            ▼
       HTTP 409 Conflict
```

Response:

```json
{
  "status": 409,
  "error": "EMAIL_ALREADY_EXISTS",
  "message": "User already exists with email: yash@example.com",
  "path": "/api/users"
}
```

---

# 🔍 Validation vs Business Errors

One of the main concepts explored in this project is the difference
between invalid input and an invalid business operation.

### Validation Error

```text
Email = "hello"
        │
        ▼
Invalid email format
        │
        ▼
HTTP 400
```

### Business Error

```text
Email = "yash@example.com"
        │
        ▼
Email already exists
        │
        ▼
HTTP 409
```

The input can be syntactically valid while the requested operation is
still rejected by a business rule.

---

# Unexpected Errors

Unexpected exceptions are handled by a generic fallback:

```java
@ExceptionHandler(Exception.class)
```

The client receives a safe response:

```json
{
  "status": 500,
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred",
  "path": "/api/users"
}
```

Internal exception details should not be exposed to API clients.

Instead:

```text
Client
  └── Safe error response

Server
  └── Detailed exception
      └── Stack trace
```

This prevents implementation details such as database errors,
framework internals, and stack traces from leaking through the API.

---

# API Error Model

The project uses structured error responses instead of returning raw
exception messages.

A general API error contains information such as:

```text
timestamp
status
error
message
path
```

Validation errors additionally contain:

```text
fieldErrors
globalErrors
```

This allows API consumers to distinguish between:

* HTTP-level status
* Application-level error code
* Human-readable message
* Field-specific validation failures
* Object-level validation failures

---

# Database Constraints

Application-level checks are not treated as the final protection for
data integrity.

For example, before creating a user:

```text
Application
    │
    └── Check whether email exists
```

helps provide a meaningful API response.

But the database should still enforce:

```text
UNIQUE(email)
```

The application check improves the API experience, while the database
constraint protects the invariant against concurrent operations.

---

# Error Scenarios Tested

The project includes experiments for:

* Invalid email
* Blank fields
* Invalid age
* Multiple validation failures
* Cross-field password validation
* Missing users
* Duplicate emails
* Unexpected runtime exceptions
* Generic 500 responses
* Global exception handling
* Specific vs generic exception handlers

---

#  Key Concepts

Through this project, the following concepts were explored:

### Exception Boundaries

Business/application exceptions should not depend on HTTP classes.

### Global Exception Handling

Controllers should not contain repetitive exception handling logic.

### Error Contracts

API errors should have a predictable and intentional structure.

### Machine-Readable Error Codes

Clients should not have to parse human-readable messages to determine
what happened.

### Information Leakage

Internal exception details should not be returned directly to clients.

### Validation Modeling

Field-level and object-level validation failures represent different
kinds of errors.

### Database Invariants

Application checks should not replace database constraints.

### Specific vs Generic Handlers

Specific exception handlers provide meaningful responses while a generic
handler acts as the final safety net.

---

# Running the Project

## Prerequisites

Make sure you have installed:

* Java 21
* Maven
* PostgreSQL
* Docker (if using the project's containerized database setup)

## Clone

```bash
git clone <your-repository-url>
cd api-error-lab
```

## Configure

Configure the required database properties in:

```text
application.properties
```

or through environment variables.

## Run

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---