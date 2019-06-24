package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPABookService implements BookService {

    private final BookRepository bookRepository;

    public JPABookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findOne(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> save(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    @Override
    public Book delete(Long id) {
        Book book = bookRepository.getOne(id);
        if (book != null) {
            bookRepository.delete(book);
            return book;
        }
        throw new IllegalArgumentException("Trying to delete non-existant entity");
    }
}
