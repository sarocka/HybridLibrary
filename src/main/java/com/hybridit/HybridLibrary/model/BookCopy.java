package com.hybridit.HybridLibrary.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    private String libraryNum;
    @Column
    private LocalDate dateOfBorrowing;
    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;
    @OneToOne (fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    private Customer customer;

    public BookCopy(String libraryNum) {
        this.libraryNum = libraryNum;
    }

    public BookCopy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibraryNum() {
        return libraryNum;
    }

    public void setLibraryNum(String libraryNum) {
        this.libraryNum = libraryNum;
    }

    public LocalDate getDateOfBorrowing() {
        return dateOfBorrowing;
    }

    public void setDateOfBorrowing(LocalDate dateOfBorrowing) {
        this.dateOfBorrowing = dateOfBorrowing;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        if (!book.getBookCopies().contains(this)) {
            book.getBookCopies().add(this);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return Objects.equals(id, bookCopy.id) &&
                Objects.equals(libraryNum, bookCopy.libraryNum) &&
                Objects.equals(dateOfBorrowing, bookCopy.dateOfBorrowing) &&
                Objects.equals(book, bookCopy.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libraryNum, dateOfBorrowing, book);
    }
}
