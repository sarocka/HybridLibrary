package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Author;

import java.util.List;

public interface AuthorService {

    Author findOne(Long id);
    List<Author> findAll();
    Author save (Author author);
    Author delete(Long id);
    Author update(Author author, Long id);
}
