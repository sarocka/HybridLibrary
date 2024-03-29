package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.BookDTO;
import com.hybridit.HybridLibrary.model.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookToBookDTOConverter implements Converter<Book, BookDTO> {

    @Override
    public BookDTO convert(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setTitle(book.getTitle());
        return bookDTO;
    }

    public List<BookDTO> convert(List<Book> books) {
        return books.stream().map(book -> convert(book)).collect(Collectors.toList());
    }


}
