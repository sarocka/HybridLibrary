package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
       return dtos.stream().map(dto->convert(dto)).collect(Collectors.toList());
    }

}
