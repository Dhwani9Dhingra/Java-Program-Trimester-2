-- 1. Create database
CREATE DATABASE IF NOT EXISTS student_task_db;
USE student_task_db;

-- 2. Create 'users' table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Create 'tasks' table
CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    task_date DATE NOT NULL,
    task_type ENUM('ASSIGNMENT', 'EXAM', 'HOMEWORK', 'HOLIDAY') DEFAULT 'HOMEWORK',
    status ENUM('PENDING', 'COMPLETED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Optional: Insert a test user (with plain text password for now)
INSERT INTO users (username, email, password, salt)
VALUES ('testuser', 'test@example.com', 'testpass', 'plaintextsalt');
