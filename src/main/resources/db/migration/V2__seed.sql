INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_FLEET_MANAGER');

INSERT INTO vehicle_types (code, display_name) VALUES
('HATCHBACK','Hatchback'),
('SEDAN','Sedan'),
('SUV','SUV');

INSERT INTO cities (name, timezone) VALUES ('New Delhi','Asia/Kolkata'), ('Mumbai','Asia/Kolkata');

INSERT INTO branches (city_id, name, address) VALUES
(1,'Noida Sector 62','Noida Sector 62, Noida'),
(2,'Mumbai Andheri','Andheri, Mumbai');
