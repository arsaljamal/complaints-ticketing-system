CREATE TABLE roles (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
description varchar(100) DEFAULT NULL,
role_name varchar(100) DEFAULT NULL
);


CREATE TABLE users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
username varchar(255) NOT NULL,
password varchar(255) NOT NULL
);

CREATE TABLE users_roles (
user_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES users (id),
CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE tickets (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
delivery_id BIGINT NOT NULL,
customer_type varchar(100) NOT NULL,
delivery_status varchar(100) NOT NULL,
rider_rating varchar(100) NOT NULL,
time_to_reach_destination TIMESTAMP NOT NULL,
expected_delivery_time TIMESTAMP NOT NULL,
expected_time_of_delivery TIMESTAMP NOT NULL
);