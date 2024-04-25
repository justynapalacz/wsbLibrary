--liquibase formatted sql
--changeset Justyna Palacz:2
INSERT INTO users(first_name, last_name,date_of_birth, login, password, email) VALUES('Jan', 'Nowak', '1991-09-11','jannowak','jannowak', 'jan@wp.pl');
INSERT INTO users(first_name, last_name,date_of_birth, login, password, email) VALUES('Adam', 'Kowalski', '1988-01-11','adamkowalski','adamkowalski', 'akow@onet.pl');
INSERT INTO users(first_name, last_name,date_of_birth, login, password, email) VALUES('Ewa', 'Olszewska', '1988-01-11','ewaolszewska','ewaolszewska', 'eolszewska@o2.pl');

INSERT INTO books(title, author,status) VALUES('Pan Tadeusz', 'Adam Mickiewicz', 1);
INSERT INTO books(title, author,status) VALUES('Dziady', 'Adam Mickiewicz', 0);
INSERT INTO books(title, author,status) VALUES('Afryka Kazika', 'Lukasz Wierzbicki', 1);
INSERT INTO books(title, author,status) VALUES('Genesis', 'Chris Carter', 0);
INSERT INTO books(title, author,status) VALUES('Złap mnie', 'Lisa Gardner', 1);
INSERT INTO books(title, author,status) VALUES('To', 'Stephen King', 1);

INSERT INTO borrow(user_id, book_id, date_of_borrow, date_of_return) VALUES((SELECT id FROM users WHERE login='jannowak'),(SELECT id FROM books WHERE title='Pan Tadeusz'), '2024-03-28 21:05:39', '2024-03-28 21:06:44');
INSERT INTO borrow(user_id, book_id, date_of_borrow, date_of_return) VALUES((SELECT id FROM users WHERE login='adamkowalski'),(SELECT id FROM books WHERE title='Dziady'), '2024-02-28 21:06:01', null);
INSERT INTO borrow(user_id, book_id, date_of_borrow, date_of_return) VALUES((SELECT id FROM users WHERE login='jannowak'),(SELECT id FROM books WHERE title='Genesis'), '2024-02-20 21:06:01', null);
INSERT INTO borrow(user_id, book_id, date_of_borrow, date_of_return) VALUES((SELECT id FROM users WHERE login='ewaolszewska'),(SELECT id FROM books WHERE title='To'), '2024-02-28 21:05:39', '2024-03-15 21:06:44');

INSERT INTO employees(first_name, last_name,date_of_birth, login, password, email, role) VALUES('Justyna', 'Palacz', '1984-01-01','Justyna','Justyna', 'jpal@gmail.com', 'ADMIN');
INSERT INTO employees(first_name, last_name,date_of_birth, login, password, email, role) VALUES('Marcin', 'Kowal', '1987-05-05','Marcin','Marcin', 'marcin@onet.pl', 'LIBRARIAN');