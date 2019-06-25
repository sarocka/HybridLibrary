package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JPABookService implements BookService {

    private final BookRepository bookRepository;

    public JPABookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findOne(Long id) {
        Book book = bookRepository.getOne(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non-existant id");
        }
        return bookRepository.getOne(id);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        if (books == null || books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books to display");
        }
        return books;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book delete(Long id) {
        Book book = bookRepository.getOne(id);
        if (book != null) {
            bookRepository.delete(book);
            return book;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The entity with a given id does not exist");
    }

    @Override
    public Book update(Book book, Long id) {
        if (id.equals(book.getId())) {
            return bookRepository.save(book);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct book id");
        }
    }
}
