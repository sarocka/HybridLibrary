package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.BookCopy;

import java.util.List;

public interface BookCopyService {

    BookCopy findOne(Long id);

    List<BookCopy> findAll();

    BookCopy save(BookCopy bookCopy);

    BookCopy delete(Long id);

    BookCopy update(BookCopy bookCopy, Long id);
}
