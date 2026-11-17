# Employee Tracker — SAST Training Application

A Spring Boot REST API built for the **T-04 SAST Vulnerability Remediation** workshop.  
The application works correctly from a business perspective, but contains **eight deliberate SAST vulnerabilities** for you to find and fix.

---

## About the application

The Employee Tracker exposes three functional areas:

| Area | Endpoints |
|------|-----------|
| Employee management | Create, list, search, and retrieve employees |
| Authentication | Register users and log in |
| Reports | Download files from a reports directory |

Each area contains at least one security vulnerability flagged by a SAST scanner. Your job is to fix every finding without breaking the existing tests.

### SAST findings

| ID | Vulnerability | File | CWE |
|----|--------------|------|-----|
| V1 | SQL Injection | `EmployeeService.java` | CWE-89 |
| V2 | Weak Cryptography (MD5) | `AuthService.java` | CWE-327 |
| V3 | Hardcoded Secret | `AuthService.java` | CWE-798 |
| V4 | Sensitive Data in Logs | `AuthController.java` | CWE-532 |
| V5 | Path Traversal | `ReportController.java` | CWE-22 |
| V6 | Verbose Error Disclosure | `GlobalExceptionHandler.java` | CWE-209 |
| V7 | Insecure Direct Object Reference | `EmployeeController.java` | CWE-639 |
| V8 | Reflected XSS | `StatusController.java` | CWE-79 |

---

## Prerequisites

| Tool | Version | Check |
|------|---------|-------|
| JDK | 17 or newer | `java -version` |
| Git | any recent | `git --version` |
| IDE | IntelliJ IDEA or VS Code with Extension Pack for Java | — |

> **Gradle is bundled** — no installation needed. The `gradlew.bat` / `gradlew` wrapper downloads Gradle automatically on first use.

---

## 1. Clone the repository

```bash
git clone https://github.com/antonypaul001/sast.git
cd sast
```

---

## 2. Build the application

```bash
.\gradlew.bat build
```

This compiles the source, runs the tests, and assembles the JAR. You should see:

```
BUILD SUCCESSFUL
```

> On macOS / Linux use `./gradlew build` instead of `.\gradlew.bat build`.

---

## 3. Run the application

```bash
.\gradlew.bat bootRun
```

The app starts on **http://localhost:8080**.  
Watch the console for:

```
Started SastTrackerApplication
```

That line means the app is ready.

Stop it at any time with **Ctrl+C**.

---

## 4. Test the application

Run the full test suite:

```bash
.\gradlew.bat test
```

Expected output:

```
BUILD SUCCESSFUL
12 tests completed, 0 failures
```

> **Do not modify any test file.** The tests describe the correct behaviour of the application. When you fix a vulnerability, the tests must still pass.

---

## 5. Try the API

With the app running, use `curl`, Postman, or Bruno to call each endpoint.

### Create an employee

```bash
curl -X POST http://localhost:8080/api/employees \
     -H "Content-Type: application/json" \
     -d "{\"name\":\"Alice Smith\",\"role\":\"Engineer\",\"email\":\"alice@co.com\",\"onboardingDate\":\"2026-12-01\"}"
```

### List all employees

```bash
curl http://localhost:8080/api/employees
```

### Search by name

```bash
curl "http://localhost:8080/api/employees/search?name=alice"
```

### Register a user

```bash
curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d "{\"username\":\"frank\",\"password\":\"pass123\"}"
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d "{\"username\":\"frank\",\"password\":\"pass123\"}"
```

> Watch the console after login — you will see the password printed in the log. That is **Vulnerability V4**.

### Get employee profile (sensitive fields — V7)

```bash
curl http://localhost:8080/api/employees/1/profile
```

> Returns salary and national ID with no authorization check. That is **Vulnerability V7**.

### Status page (V8)

```bash
curl "http://localhost:8080/api/status?message=Hello"
```

> Try injecting `<script>alert(1)</script>` as the message value in a browser. That is **Vulnerability V8**.

---

## 6. H2 database console

An in-browser SQL console is available while the app is running:

- URL: http://localhost:8080/h2-console  
- JDBC URL: `jdbc:h2:mem:sastdb`  
- Username: `sa`  
- Password: *(leave blank)*

---

## Project structure

```
sast-tracker/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
└── src/
    ├── main/
    │   ├── java/com/accenture/sast/tracker/
    │   │   ├── SastTrackerApplication.java
    │   │   ├── controller/
    │   │   │   ├── AuthController.java       ← V4 Sensitive logging
    │   │   │   ├── EmployeeController.java   ← V7 IDOR (profile endpoint)
    │   │   │   ├── GlobalExceptionHandler.java ← V6 Verbose error disclosure
    │   │   │   ├── ReportController.java     ← V5 Path traversal
    │   │   │   └── StatusController.java     ← V8 Reflected XSS
    │   │   ├── model/
    │   │   ├── repository/
    │   │   └── service/
    │   │       ├── AuthService.java          ← V2 Weak crypto, V3 Hardcoded secret
    │   │       └── EmployeeService.java      ← V1 SQL injection
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/com/accenture/sast/tracker/
            ├── AuthControllerTest.java
            └── EmployeeControllerTest.java
```

---

## Tech stack

| Layer | Choice |
|-------|--------|
| Framework | Spring Boot 3.5 |
| Database | H2 in-memory |
| Build | Gradle 9.7 (wrapper included) |
| Java | 17+ |
| Tests | JUnit 5 + Spring MockMvc |
