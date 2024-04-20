--liquibase formatted sql
--changeset Justyna Palacz:1
CREATE TABLE users (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR (100),
  last_name VARCHAR(100),
  login VARCHAR(100),
  password VARCHAR(100),
  date_of_birth DATE NOT NULL,
  email VARCHAR(100)
);

CREATE TABLE books (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR (100),
  author VARCHAR(100),
  status boolean not null default 1
);

CREATE TABLE borrow (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT UNSIGNED NOT NULL,
  book_id INT UNSIGNED NOT NULL,
  date_of_borrow DATETIME NOT NULL,
  date_of_return DATETIME,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE employees (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR (100),
  last_name VARCHAR(100),
  login VARCHAR(100),
  password VARCHAR(100),
  date_of_birth DATE NOT NULL,
  email VARCHAR(100),
  role VARCHAR(100) NOT NULL
);
