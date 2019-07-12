package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.model.Customer;
import com.hybridit.HybridLibrary.repository.BookCopyRepository;
import com.hybridit.HybridLibrary.repository.BookRepository;
import com.hybridit.HybridLibrary.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JPABookCopyService implements BookCopyService {

    public final BookCopyRepository bookCopyRepository;
    public final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    @Value("${maximumDaysForKeepingRentedBooks}")
    private int maximumDaysForKeepingRentedBooks;


    public JPABookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository, CustomerRepository customerRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
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

    @Override
    public BookCopy rentByBookTitle(String bookTitle, String membershipNo) {
        Book book = bookRepository.findByTitle(bookTitle);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with a given title does not exist.");
        }
        Customer customer = customerRepository.findByMembershipNo(membershipNo);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer does not exist.");
        }
        BookCopy rented = findFirstAvailableCopyByBookId(book.getId());
        if (rented == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No available copies to rent");
        }
        rented.setCustomer(customer);
        rented.setDateOfBorrowing(new Date());
        bookCopyRepository.save(rented);
        return rented;
    }

    private BookCopy findFirstAvailableCopyByBookId(Long id) {
        return bookCopyRepository.findFirstByBookIdAndDateOfBorrowingNull(id);
    }

    @Override
    public BookCopy returnCopy(String libraryNum) {
        BookCopy returned = bookCopyRepository.findByLibraryNum(libraryNum);
        if (returned == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Copy with a given libraryNum does not exist.");
        }
        returned.setDateOfBorrowing(null);
        returned.setCustomer(null);
        bookCopyRepository.save(returned);
        return returned;
    }

    @Override
    public BookCopy rentByLibraryNum(String libraryNum, String membershipNo) {
        Customer customer = customerRepository.findByMembershipNo(membershipNo);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer does not exist.");
        }
        BookCopy rented = bookCopyRepository.findByLibraryNum(libraryNum);
        if (rented == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Copy with a given libraryNum does not exist.");
        }
        rented.setDateOfBorrowing(new Date());
        rented.setCustomer(customer);
        bookCopyRepository.save(rented);
        return rented;
    }

    public List<BookCopy> getOverdueCopies() {
        List<BookCopy> rentedCopies = bookCopyRepository.findByDateOfBorrowingNotNull();
        List<BookCopy> overdueCopies = new ArrayList<>();
        if (rentedCopies.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No rented copies");
        }
        for (BookCopy copy : rentedCopies) {
            if (getDifferenceInDays(copy.getDateOfBorrowing()) >= maximumDaysForKeepingRentedBooks) {
                overdueCopies.add(copy);
            }
        }
        if (overdueCopies.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No overdue copies");
        }
        return overdueCopies;
    }

    public int getDifferenceInDays(Date dateOfBorrowing) {
        Date currentDate = new Date();
        long currentLong = currentDate.getTime();
        long dateOfBorrowingLong = dateOfBorrowing.getTime();
        long diffLong = currentLong - dateOfBorrowingLong;
        return (int) (diffLong / (1000 * 60 * 60 * 24));
    }
}
