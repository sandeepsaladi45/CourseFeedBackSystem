CREATE DATABASE IF NOT EXISTS feedback_system;
USE feedback_system;

-- Table for students
CREATE TABLE IF NOT EXISTS students (
    sno INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    registration_number VARCHAR(50) UNIQUE NOT NULL,
    login_status BOOLEAN DEFAULT FALSE
);

-- Table for courses
CREATE TABLE IF NOT EXISTS courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL
);

-- Table for feedback
CREATE TABLE IF NOT EXISTS feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    student_sno INT,
    course_id INT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comments TEXT,
    FOREIGN KEY (student_sno) REFERENCES students(sno),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

-- Table for semester feedback (as used in the fancy dashboard)
CREATE TABLE IF NOT EXISTS semester_feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    semester INT,
    subject_name VARCHAR(100),
    rating INT,
    comment TEXT
);

-- Sample Data
INSERT INTO students (name, email, registration_number) VALUES 
('Sandeep Saladi', 'sandeep@gmail.com', '12345'),
('Alice Smith', 'alice@gmail.com', '67890');

INSERT INTO courses (course_name) VALUES 
('Mathematics'),
('Programming in C'),
('Physics'),
('Chemistry Lab'),
('Workshop');
