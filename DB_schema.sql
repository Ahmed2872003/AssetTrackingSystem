CREATE DATABASE asset_tracking_system;
USE asset_tracking_system;

CREATE TABLE Role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) COLLATE utf8mb4_bin UNIQUE
);

CREATE TABLE Status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) COLLATE utf8mb4_bin UNIQUE
);

CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) COLLATE utf8mb4_bin NOT NULL UNIQUE,
    role_id INT NOT NULL,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (role_id) REFERENCES Role(id)
);

CREATE TABLE Asset (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
    category VARCHAR(100) NOT NULL,
    status_id INT NOT NULL DEFAULT(2),
    staff_id INT,
    asset_manager_id INT NOT NULL,
    FOREIGN KEY (status_id) REFERENCES Status(id),
    FOREIGN KEY (staff_id) REFERENCES User(id),
    FOREIGN KEY (asset_manager_id) REFERENCES User(id)
);

CREATE TABLE Asset_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status_id INT NOT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    staff_id INT,
    asset_id INT NOT NULL,
    FOREIGN KEY (status_id) REFERENCES Status(id),
    FOREIGN KEY (staff_id) REFERENCES User(id),
    FOREIGN KEY (asset_id) REFERENCES Asset(id) ON DELETE CASCADE
);
INSERT INTO Role (name) VALUES 
('admin'),
('asset_manager'),
('staff');

INSERT INTO Status (name) VALUES 
('assigned'),
('available'),
('under_maintainance');

INSERT INTO User (name, role_id, password)
VALUES ("admin", 1, "$2a$10$GLgFtvzcn1zDVp1I8hQ.ouhmmt/U9bFQS4TRafnQ.PfIVXC8sdN7m")
