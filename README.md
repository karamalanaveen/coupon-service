# Coupon Service REST API

A microservice built with **Java 17**, **Spring Boot 3**, **Spring Data JPA**, and **MySQL**.

---

## 📌 Features

1. **Coupon Entity with JPA**:
   - `id`: Auto-generated unique ID (`GenerationType.IDENTITY`).
   - `code`: Unique coupon code (mandatory, e.g. `SUMMER20`).
   - `discount`: Discount amount or percentage (mandatory, positive `BigDecimal`).
   - `expDate`: Expiration date (`LocalDate`, e.g. `2026-12-31`).
2. **Spring Data JPA Repository**:
   - Extends `JpaRepository<Coupon, Long>` with lookup by coupon code (`findByCode`) and uniqueness check (`existsByCode`).
3. **Service Layer**:
   - Clean interface and implementation with transactional management.
   - Complete CRUD operations: Create, Read (all, by ID, by Code), Update, Delete.
   - Custom `ResourceNotFoundException` mapped to `404 NOT FOUND`.
4. **REST Controller (`/api/coupons`)**:
   - Standard HTTP response codes: `201 CREATED`, `200 OK`, `404 NOT FOUND`, `400 BAD REQUEST`.
   - Global exception handling via `@RestControllerAdvice` with structured error responses.
5. **Flexible Configuration**:
   - Configurable server port (default `8082`) and MySQL datasource (`coupon_db`) via environment variables.

---

## 🏗️ Architecture & Package Layout

```
src/main/java/com/example/couponservice/
├── CouponServiceApplication.java
├── controller/
│   └── CouponController.java
├── dto/
│   ├── CouponDto.java
│   └── ErrorResponse.java
├── entity/
│   └── Coupon.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository/
│   └── CouponRepository.java
└── service/
    ├── CouponService.java
    └── impl/
        └── CouponServiceImpl.java
```

---

## ⚙️ Configuration & Environment Variables

Settings are configured in `src/main/resources/application.properties` and can be overridden via environment variables:

| Environment Variable | Description | Default Value |
|----------------------|-------------|---------------|
| `SERVER_PORT` | Port on which the Spring Boot application runs | `8082` |
| `DB_URL` | JDBC URL for MySQL database | `jdbc:mysql://localhost:3306/coupon_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true` |
| `DB_USERNAME` | MySQL database username | Required |
| `DB_PASSWORD` | MySQL database password | Required |

Set `DB_USERNAME` and `DB_PASSWORD` in the environment before starting the service. Do not commit database credentials to source control.

### Setting Up MySQL Database

```sql
CREATE DATABASE IF NOT EXISTS coupon_db;
```

---

## 🚀 Building & Running

```bash
# Navigate to coupon-service folder
cd coupon-service

# Build the project
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run
```

---

## 📡 API Endpoints

Base URL: `http://localhost:8082/api/coupons`

### 1. Create Coupon
* **Method:** `POST`
* **URL:** `/api/coupons`
* **Status:** `201 CREATED`
* **Request Body:**
```json
{
  "code": "SUMMER20",
  "discount": 20.00,
  "expDate": "2026-12-31"
}
```
* **Sample cURL:**
```bash
curl -X POST http://localhost:8082/api/coupons \
  -H "Content-Type: application/json" \
  -d '{
    "code": "SUMMER20",
    "discount": 20.00,
    "expDate": "2026-12-31"
  }'
```

---

### 2. Get All Coupons
* **Method:** `GET`
* **URL:** `/api/coupons`
* **Status:** `200 OK`
* **Sample cURL:**
```bash
curl -X GET http://localhost:8082/api/coupons
```

---

### 3. Get Coupon By ID
* **Method:** `GET`
* **URL:** `/api/coupons/{id}`
* **Status:** `200 OK` (or `404 NOT FOUND` if ID does not exist)
* **Sample cURL:**
```bash
curl -X GET http://localhost:8082/api/coupons/1
```

---

### 4. Get Coupon By Code
* **Method:** `GET`
* **URL:** `/api/coupons/code/{code}`
* **Status:** `200 OK` (or `404 NOT FOUND` if code does not exist)
* **Sample cURL:**
```bash
curl -X GET http://localhost:8082/api/coupons/code/SUMMER20
```

---

### 5. Update Coupon
* **Method:** `PUT`
* **URL:** `/api/coupons/{id}`
* **Status:** `200 OK` (or `404 NOT FOUND` if ID does not exist)
* **Request Body:**
```json
{
  "code": "SUMMER25",
  "discount": 25.00,
  "expDate": "2026-12-31"
}
```
* **Sample cURL:**
```bash
curl -X PUT http://localhost:8082/api/coupons/1 \
  -H "Content-Type: application/json" \
  -d '{
    "code": "SUMMER25",
    "discount": 25.00,
    "expDate": "2026-12-31"
  }'
```

---

### 6. Delete Coupon
* **Method:** `DELETE`
* **URL:** `/api/coupons/{id}`
* **Status:** `200 OK` (or `404 NOT FOUND` if ID does not exist)
* **Sample Response:**
```json
{
  "message": "Coupon deleted successfully with id: 1",
  "status": 200
}
```
* **Sample cURL:**
```bash
curl -X DELETE http://localhost:8082/api/coupons/1
```
