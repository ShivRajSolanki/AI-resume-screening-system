# User registration Module 

## 1. What did we build?

we built the User registration feature for our smart resume Screening system.

The Hr can register using 
Name,
Email,
Password

After succesful registration the user stored in mySql database

----

## 3. Complete Request Flow

### Step 1 : Client (Postman)

The HR fills in the registration form and clicks the **Register** button.

The frontend sends an HTTP POST request.

```http
POST /api/auth/register
```

Request Body

```json
{
   "name":"Shivraj",
   "email":"shivraj@gmail.com",
   "password":"Password@123"
}
```

↓

### Step 2 : AuthController

The request reaches Spring Boot's embedded Tomcat server.

Tomcat forwards the request to Spring's DispatcherServlet.

DispatcherServlet checks all controller mappings and finds:

```java
@PostMapping("/register")
public RegisterResponse register(
        @Valid @RequestBody RegisterRequest request);
```

Spring performs two things automatically:

- Converts JSON into a `RegisterRequest` object.
- Validates the request using `@Valid`.

The controller **does not contain business logic**.

Its only job is to receive the request and forward it to the service.

↓

### Step 3 : UserService

This is where the business logic happens.

The service:

- Checks if the email already exists.
- Creates a new `User` entity.
- Encrypts the password using BCrypt.
- Assigns the default role (`HR`).
- Calls the repository to save the user.

↓

### Step 4 : UserRepository

The repository is responsible for database operations.

We call:

```java
userRepository.save(user);
```

We do not write SQL manually.

Instead, Spring Data JPA forwards the entity to Hibernate.

↓

### Step 5 : Hibernate

Hibernate is the JPA implementation.

Its job is to convert the Java `User` object into SQL.

Example:

```sql
INSERT INTO users(name,email,password,role)
VALUES (...);
```

Hibernate executes this SQL automatically.

↓

### Step 6 : MySQL

MySQL receives the SQL statement and stores the new user.

The password stored in the database is the BCrypt hash, not the original password.

↓

### Step 7 : RegisterResponse

After the user is saved successfully,

the service creates a response object.

Example:

```java
return new RegisterResponse(
        "User Registered Successfully"
);
```

↓

### Step 8 : Spring Boot

Spring converts the `RegisterResponse` Java object into JSON automatically.

```json
{
   "message":"User Registered Successfully"
}
```

↓

### Step 9 : Client

The JSON response is sent back to the client.

The frontend displays:

```
User Registered Successfully
```

---

### Complete Flow Diagram

```
Client (Postman)

        │

        ▼

Embedded Tomcat

        │

        ▼

DispatcherServlet

        │

        ▼

AuthController

        │

        ▼

UserService

        │

        ▼

UserRepository

        │

        ▼

Hibernate

        │

        ▼

      MySQL

        │

        ▼

RegisterResponse

        │

        ▼

Spring converts Java Object → JSON

        │

        ▼

Client
```
# 4. Concepts Learned

## 4.1 DTO

### Problem

If the frontend sends the User Entity directly, it can modify fields that should never be controlled by the client.

Example:

- id
- role
- createdAt

This is unsafe.

### Solution

We created RegisterRequest.

Only required fields are accepted.

```
name
email
password
```

Then inside the service we create the User entity ourselves.

### How we used it

```
Client

↓

RegisterRequest

↓

User Entity

↓

Database
```

### Interview Questions

- Why DTO?
- Why not use Entity directly?
- What problem does DTO solve?

---

## 4.2 Service Layer

### Problem

If all business logic is written inside the controller,

every controller that needs registration must duplicate the same code.

Example:

```
Register Controller

↓

Check Email

↓

Encrypt Password

↓

Save User
```

Admin registration would duplicate everything.

### Solution

Move business logic into UserService.

Controllers only receive requests and return responses.

### How we used it

```
Controller

↓

UserService

↓

Repository
```

### Interview Questions

- Why Service Layer?
- Why shouldn't business logic be inside Controller?
----

## 4.3 BCrypt Password Encryption

### Problem

Initially we stored passwords like this:

```
Password@123
```

If the database gets hacked,

every user's password becomes visible.

### Solution

Use BCryptPasswordEncoder.

Passwords are converted into a one-way hash before storing.

Example:

```
Password@123

↓

$2a$10$Ajs8xk....
```

### How we used it

```java
user.setPassword(
    passwordEncoder.encode(request.getPassword())
);
```

### Interview Questions

- Why BCrypt?
- Why not MD5?
- Why not store passwords directly?
- How does login work if the password cannot be decrypted?

---

## 4.4 Global Exception Handler

### Problem

Without exception handling,

Spring returns a huge default error response.

```
500 Internal Server Error
```

Not user friendly.

### Solution

Create a GlobalExceptionHandler.

Whenever EmailAlreadyExistsException is thrown,

Spring automatically calls our handler.

### Flow

```
UserService

↓

throw EmailAlreadyExistsException

↓

GlobalExceptionHandler

↓

409 CONFLICT

↓

Client
```

### Interview Questions

- Why Global Exception Handler?
- Explain Exception Flow.
- Why use @RestControllerAdvice?

---

# 5. Mistakes We Made

Initially we stored passwords directly.

Later we replaced them with BCrypt.

---

Initially we used RuntimeException.

Later we created EmailAlreadyExistsException.

---

Initially Spring returned 401 Unauthorized after adding Spring Security.

We fixed it using SecurityConfig.

---

# 6. Production Improvements

This module is working but still needs:

- Email Verification
- Login API
- JWT Authentication
- Refresh Tokens
- Rate Limiting
- Audit Logs
- Unit Tests

---

# 7. Quick Revision

Registration Flow

```
Client

↓

Controller

↓

Validation

↓

Service

↓

Repository

↓

Hibernate

↓

MySQL

↓

Response
```

Remember:

- Controller → Receive Request
- Service → Business Logic
- Repository → Database
- DTO → Safe Data Transfer
- BCrypt → Password Security
- Exception Handler → Clean Error Responses