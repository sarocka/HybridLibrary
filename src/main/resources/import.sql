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

