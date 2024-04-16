--liquibase formatted sql
--changeset Justyna Palacz:2
INSERT INTO users(id, first_name, last_name,date_of_birth, login, password) VALUE(1,'Jan', 'Nowak', '1991-09-11','jannowak','jannowak');
INSERT INTO users(id, first_name, last_name,date_of_birth, login, password) VALUE(2,'Adam', 'Kowalski', '1988-01-11','adamkowalski','adamkowalski');
INSERT INTO users(id, first_name, last_name,date_of_birth, login, password) VALUE(3,'Ewa', 'Olszewska', '1988-01-11','ewaolszewska','ewaolszewska');

INSERT INTO books(id, title, author,status) VALUE(1,'Pan Tadeusz', 'Adam Mickiewicz', 1);
INSERT INTO books(id, title, author,status) VALUE(2,'Dziady', 'Adam Mickiewicz', 0);
INSERT INTO books(id, title, author,status) VALUE(3,'Afryka Kazika', 'Lukasz Wierzbicki', 1);
INSERT INTO books(id, title, author,status) VALUE(4,'Genesis', 'Chris Carter', 0);
INSERT INTO books(id, title, author,status) VALUE(5,'Złap mnie', 'Lisa Gardner', 1);
INSERT INTO books(id, title, author,status) VALUE(6,'To', 'Stephen King', 1);

INSERT INTO borrow(id, user_id, book_id,date_of_borrow, date_of_return) VALUE(1,1,1, "2024-03-28 21:05:39", "2024-03-28 21:06:44");
INSERT INTO borrow(id, user_id, book_id,date_of_borrow, date_of_return) VALUE(2,2,2, "2024-02-28 21:06:01", null);
INSERT INTO borrow(id, user_id, book_id,date_of_borrow, date_of_return) VALUE(3,1,4, "2024-02-20 21:06:01", null);
INSERT INTO borrow(id, user_id, book_id,date_of_borrow, date_of_return) VALUE(4,3,5, "2024-02-28 21:05:39", "2024-03-15 21:06:44");
