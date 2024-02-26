-- ROLE
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('CUSTOMER');
INSERT INTO roles (name) VALUES ('GUEST');
INSERT INTO roles (name) VALUES ('SELLER');

-- USER
INSERT INTO users (name, email, contact, password, is_active, role_id) VALUES ('ADMIN', 'admin@saveursolidaire.com', '0123456789', '$2a$10$XWIILyGo6l9W/Lkac91rvOZNV/Qfz33U0H1RNOJ47uLcW8HL0Vxzq', 1, 1);
INSERT INTO users (name, email, contact, password, is_active, role_id) VALUES ('USER', 'user@saveursolidaire.com', '0223456789', '$2a$10$XWIILyGo6l9W/Lkac91rvOZNV/Qfz33U0H1RNOJ47uLcW8HL0Vxzq', 1, 2);

-- STORE
INSERT INTO stores (name, contact, description, is_active, address, city, country, postal_code, complement, latitude, longitude) VALUES ('STORE', '0123456789', 'DESCRIPTION', 1, 'ADDRESS', 'CITY', 'COUNTRY', '00000', 'COMPLEMENT', 1.0, 1.0);

-- USER_STORE
INSERT INTO user_store (user_id, store_id, is_active) VALUES (1, 1, 1);
