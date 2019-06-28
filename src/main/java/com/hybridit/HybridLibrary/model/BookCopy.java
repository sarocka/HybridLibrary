package com.hybridit.HybridLibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity

public class BookCopy {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotNull
    private String libraryNum;
    @Column
    private Date dateOfBorrowing;

    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;

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

    public Date getDateOfBorrowing() {
        return dateOfBorrowing;
    }

    public void setDateOfBorrowing(Date dateOfBorrowing) {
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
}
