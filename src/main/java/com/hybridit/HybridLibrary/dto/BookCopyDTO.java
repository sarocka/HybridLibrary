package com.hybridit.HybridLibrary.dto;

import java.util.Date;
import java.util.Objects;

public class BookCopyDTO {

    private Long id;
    private String libraryNum;
    private Date dateOfBorrowing;
    private Long bookId;
    private String bookTitle;

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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopyDTO that = (BookCopyDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
