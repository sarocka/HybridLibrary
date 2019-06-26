package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookCopyDTOToBookCopyConverter implements Converter<BookCopyDTO, BookCopy> {

    private final BookService bookService;

    public BookCopyDTOToBookCopyConverter(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public BookCopy convert(BookCopyDTO bookCopyDTO) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(bookCopyDTO.getId());
        bookCopy.setDateOfBorrowing(bookCopyDTO.getDateOfBorrowing());
        bookCopy.setLibraryNum(bookCopyDTO.getLibraryNum());
        bookCopy.setBook(bookService.findOne(bookCopyDTO.getBookId()));
        return bookCopy;
    }

    public List<BookCopy> convert(List<BookCopyDTO> dtos) {
        List<BookCopy> bookCopies = new ArrayList<>();

        for (BookCopyDTO bookCopyDTO : dtos) {
            bookCopies.add(convert(bookCopyDTO));
        }
        return bookCopies;
    }

}
