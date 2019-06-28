package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
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
        return bookCopyRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity with a given id does not exist"));
    }

    @Override
    public List<BookCopy> findAll() {
        List<BookCopy> bookCopies= bookCopyRepository.findAll();
        if (bookCopies.isEmpty()){
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
        return bookCopyRepository.findById(id)
                .map(bookCopy -> {
                    bookCopyRepository.delete(bookCopy);
                    return bookCopy;
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The entity ith a given id does not exist"));
    }

    @Override
    public BookCopy update(BookCopy bookCopy, Long id) {
      if(bookCopyRepository.existsById(id)){
       return bookCopyRepository.save(bookCopy);
      }
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookcopy with a given id does not exist!");
    }
}
