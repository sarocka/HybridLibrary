package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.BookDTO;
import com.hybridit.HybridLibrary.model.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class BookDTOToBookConverter implements Converter<BookDTO, Book> {
    @Override
    public Book convert(BookDTO bookDTO) {
        Book book= new Book();
        book.setId(bookDTO.getId());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublisher(bookDTO.getPublisher());
        book.setTitle(bookDTO.getTitle());
        return book;
    }
    public List<Book> convert(List<BookDTO> dtos){
        List<Book> books = new ArrayList<>();

        for(BookDTO bookDTO : dtos){
            books.add(convert(bookDTO));
        }
        return books;
    }

}
