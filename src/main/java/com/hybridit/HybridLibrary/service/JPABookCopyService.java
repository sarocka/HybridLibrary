package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.repository.BookCopyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class JPABookCopyService implements BookCopyService {

    public final BookCopyRepository bookCopyRepository;

    public JPABookCopyService(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public BookCopy findOne(Long id) {
        return bookCopyRepository.getOne(id);
    }

    @Override
    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }

    @Override
    public BookCopy save(BookCopy copy) {
        return bookCopyRepository.save(copy);
    }

    @Override
    public BookCopy delete(Long id) {
        BookCopy bookCopy = bookCopyRepository.getOne(id);
        if (bookCopy != null) {
            bookCopyRepository.delete(bookCopy);
            return bookCopy;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The entity with a given id does not exist");
    }

    @Override
    public BookCopy update(BookCopy bookCopy, Long id) {
        if (id.equals(bookCopy.getId())) {
            return bookCopyRepository.save(bookCopy);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct book id");
        }
    }
}
