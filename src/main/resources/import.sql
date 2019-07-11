Insert into book (id, isbn, publisher, title) values (1, '4567-897-98', 'Jonathan Cape', 'Flauberts Parrot');
Insert into book (id, isbn, publisher, title) values (2, '4567-896-09', 'Oxford University Press', 'The Late Mattia Pascalt');

Insert into book_copy(id, library_num, date_of_borrowing, book_id) values (1, '4567-0987-ab', null, 1);
Insert into book_copy (id, library_num, date_of_borrowing, book_id) values (2, '4567-0987-cd', null, 1);
Insert into book_copy (id, library_num, date_of_borrowing, book_id) values (3, '4507-0987-ab', null, 2);
Insert into book_copy (id, library_num, date_of_borrowing, book_id) values (4, '4597-0987-cd', null, 2);

Insert into author (id, name) values (1, 'Julian Barnes');
Insert into author (id, name) values (2, 'Luiggi Pirandelo');

Insert into book_author (book_id, author_id) values (1, 1);
Insert into book_author (book_id, author_id) values (2, 2);

Insert into role (id, name) values (1, 'LIBRARIAN');
Insert into role (id, name) values (2, 'ADMIN');
Insert into role (id, name) values (3, 'MANAGER');

Insert into user (id, username, password, firstname, lastname, role_id) values (1, 'sara', 'petruska', 'Sara', 'Rat',1);
Insert into user (id, username, password, firstname, lastname, role_id) values (2, 'aron', 'aron', 'Aron', 'Sreder',2);
Insert into user (id, username, password, firstname, lastname, role_id) values (3, 'ivan', 'ivan', 'Ivan', 'Gligoric',3);

Insert into customer (id, firstname, lastname, address, phone_no) values (1, 'Vladimir', 'Danilovic', 'Cirpanova 1a', '063510250');





