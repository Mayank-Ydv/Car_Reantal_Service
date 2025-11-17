-- Create roles
CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

-- Users
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(255),
  phone VARCHAR(30),
  role_id INT REFERENCES roles(id),
  created_at TIMESTAMP DEFAULT now()
);

-- Cities and branches
CREATE TABLE cities (
  id SERIAL PRIMARY KEY,
  name VARCHAR(150) UNIQUE NOT NULL,
  timezone VARCHAR(50)
);
CREATE TABLE branches (
  id SERIAL PRIMARY KEY,
  city_id INT REFERENCES cities(id),
  name VARCHAR(255),
  address TEXT
);

-- Vehicle types and vehicles
CREATE TABLE vehicle_types (
  id SERIAL PRIMARY KEY,
  code VARCHAR(50) UNIQUE NOT NULL,
  display_name VARCHAR(255)
);

CREATE TABLE vehicles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  branch_id INT REFERENCES branches(id),
  vehicle_type_id INT REFERENCES vehicle_types(id),
  plate_number VARCHAR(50) UNIQUE,
  manufacturer VARCHAR(100),
  model VARCHAR(100),
  year INT,
  seats INT,
  color VARCHAR(50),
  status VARCHAR(50) DEFAULT 'AVAILABLE',
  daily_base_price NUMERIC(10,2),
  per_km_price NUMERIC(10,2),
  created_at TIMESTAMP DEFAULT now()
);

-- Bookings
CREATE TABLE bookings (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id),
  branch_pickup_id INT REFERENCES branches(id),
  branch_return_id INT REFERENCES branches(id),
  total_price NUMERIC(12,2),
  status VARCHAR(50) DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT now(),
  start_ts TIMESTAMP NOT NULL,
  end_ts TIMESTAMP NOT NULL,
  vehicle_id UUID REFERENCES vehicles(id)
);

-- Payments (simplified)
CREATE TABLE payments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  booking_id UUID REFERENCES bookings(id),
  amount NUMERIC(12,2),
  provider VARCHAR(100),
  provider_payment_id VARCHAR(255),
  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT now()
);

-- Reviews
CREATE TABLE reviews (
  id SERIAL PRIMARY KEY,
  user_id UUID REFERENCES users(id),
  vehicle_id UUID REFERENCES vehicles(id),
  rating SMALLINT CHECK (rating >=1 AND rating <=5),
  comment TEXT,
  created_at TIMESTAMP DEFAULT now()
);
