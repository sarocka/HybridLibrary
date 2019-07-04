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
        if (!bookCopyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book copy with a given id does not exist");
        }
        return bookCopyRepository.getOne(id);
    }

    @Override
    public List<BookCopy> findAll() {
        List<BookCopy> bookCopies = bookCopyRepository.findAll();
        if (bookCopies.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No book copies to display");
        }
        return bookCopies;
    }

    @Override
    public BookCopy save(BookCopy copy) {
        return bookCopyRepository.save(copy);
    }

    @Override
    public BookCopy delete(Long id) {
        if (!bookCopyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book copy with id provided does not exist");
        } else if (bookCopyRepository.getOne(id).getDateOfBorrowing() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete rented copy");
        }
        BookCopy bookCopy = bookCopyRepository.getOne(id);
        bookCopyRepository.delete(bookCopy);
        return bookCopy;
    }

    @Override
    public BookCopy update(BookCopy fromRequestBody, Long id) {
        if (!bookCopyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book copy with id provided does not exist");
        }
        BookCopy copyFromDb = bookCopyRepository.getOne(id);
        copyFromDb.setLibraryNum(fromRequestBody.getLibraryNum());
        copyFromDb.setBook(fromRequestBody.getBook());
        copyFromDb.setDateOfBorrowing(fromRequestBody.getDateOfBorrowing());
        bookCopyRepository.save(copyFromDb);
        return copyFromDb;
    }
}
