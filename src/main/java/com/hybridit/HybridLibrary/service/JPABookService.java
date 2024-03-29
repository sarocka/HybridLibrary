package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.repository.BookCopyRepository;
import com.hybridit.HybridLibrary.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class JPABookService implements BookService {

    private final BookRepository bookRepository;
    private final BookCopyService bookCopyService;
    private final BookCopyRepository bookCopyRepository;


    public JPABookService(BookRepository bookRepository, BookCopyService bookCopyService, BookCopyRepository bookCopyRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyService = bookCopyService;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public Book findOne(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id provided does not exist");
        }
        return bookRepository.getOne(id);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
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
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with provided id does not exist");
        }
        Book book = bookRepository.getOne(id);
        if (!findRentedCopies(book.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete book with rented copies");
        }
        book.getAuthors().forEach(author -> author.removeBook(book));
        book.getBookCopies().forEach(bookCopy -> bookCopyRepository.delete(bookCopy));
        return book;
    }

    public List<BookCopy> findRentedCopies(Long id) {
        return bookCopyRepository.findByBookIdAndDateOfBorrowingNotNull(id);
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
