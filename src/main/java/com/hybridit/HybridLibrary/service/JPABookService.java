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
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Non-existant id"));
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()){
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
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    book.removeBookFromAuthorsListOfBooks(book);
                    book.removeCopies();
                    return book;
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The entity with a given id does not exist"));
    }
    @Override
    public Book update(Book fromRequestBody, Long id) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(fromRequestBody.getTitle());
            book.setPublisher(fromRequestBody.getPublisher());
            book.setIsbn(fromRequestBody.getIsbn());
            bookRepository.save(book);
            return book;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with provided id does not exist"));
    }
}
