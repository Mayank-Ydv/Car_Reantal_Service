# Car Rental Service - Backend (Spring Boot)

This is a runnable Spring Boot 2.7.0 + Java 17 project skeleton for a Car Rental Service backend.

## Prerequisites
- Java 17
- Maven 3.6+
- PostgreSQL running locally with:
  - DB: `rentaldb`
  - User: `rental_user`
  - Password: `rental_pass`
- (Optional) Redis if you later enable caching/locks

## Setup
1. Create the database and user (example):
```bash
sudo -u postgres psql
CREATE DATABASE rentaldb;
CREATE USER rental_user WITH ENCRYPTED PASSWORD 'rental_pass';
GRANT ALL PRIVILEGES ON DATABASE rentaldb TO rental_user;
\q
```

2. Run the application:
```bash
mvn spring-boot:run
```

Flyway will run migrations on startup.

## Default endpoints
- POST `/api/auth/register` -> register { "email", "password" }
- POST `/api/auth/login` -> login { "email", "password" }
- GET `/api/vehicles/search?branchId=1&typeId=1`
- POST `/api/bookings` (requires Authorization: Bearer <token>)

