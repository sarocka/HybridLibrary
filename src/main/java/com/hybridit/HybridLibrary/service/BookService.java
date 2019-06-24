package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {

    Book findOne(Long id);
    List<Book> findAll();
    Book save (Book book);
    List<Book> save (List<Book>books);
    Book delete(Long id);

}
