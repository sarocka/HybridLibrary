package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book findOne(Long id);
    List<Book> findAll();
    Book save (Book book);
    Book delete(Long id);
    Book update(Book book, Long id);
}
