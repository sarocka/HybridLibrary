package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.dto.BookDTO;
import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCopyToBookCopyDTOConverter implements Converter<BookCopy, BookCopyDTO> {

    @Override
    public BookCopyDTO convert(BookCopy bookCopy) {
        BookCopyDTO bookCopyDTO = new BookCopyDTO();
        bookCopyDTO.setId(bookCopy.getId());
        bookCopyDTO.setBookTitle(bookCopy.getBook().getTitle());
        bookCopyDTO.setBookId(bookCopy.getBook().getId());
        bookCopyDTO.setDateOfBorrowing(bookCopy.getDateOfBorrowing());
        bookCopyDTO.setLibraryNum(bookCopy.getLibraryNum());
        return bookCopyDTO;
    }

    public List<BookCopyDTO> convert(List<BookCopy> bookCopies) {
        return bookCopies.stream().map(bookCopy -> convert(bookCopy)).collect(Collectors.toList());
        //return bookCopies.stream().map(this::convert).collect(Collectors.toList());
    }


}
