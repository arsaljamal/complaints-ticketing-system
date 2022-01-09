-- USER
-- hashed password: admin123
INSERT INTO users (id, username, password) VALUES (1,  'admin', '$2a$12$PGPkpI3o.UH7ZFmoV7dzVO5to5ROSNJezdLCPSV7l5S98fXsxUNUS');
INSERT INTO users (id, username, password) VALUES (2,  'arsal', '$2a$12$PGPkpI3o.UH7ZFmoV7dzVO5to5ROSNJezdLCPSV7l5S98fXsxUNUS');

-- ROLES

INSERT INTO roles (id, role_name, description) VALUES
(1, 'ROLE_ADMIN', 'Administrator'),
(2, 'ROLE_CSR', 'Customer Support Representative');

-- USERS-ROLES
INSERT INTO users_roles(user_id, role_id) VALUES
(1, 1), -- give admin ROLE_ADMIN
(2, 2);  -- give arsal ROLE_CSR