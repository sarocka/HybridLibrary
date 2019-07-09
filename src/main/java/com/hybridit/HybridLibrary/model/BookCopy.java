package com.hybridit.HybridLibrary.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
