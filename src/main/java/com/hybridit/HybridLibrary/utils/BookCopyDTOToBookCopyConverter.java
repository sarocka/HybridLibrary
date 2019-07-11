package com.hybridit.HybridLibrary.utils;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookService;
import com.hybridit.HybridLibrary.service.CustomerService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCopyDTOToBookCopyConverter implements Converter<BookCopyDTO, BookCopy> {

    private final BookService bookService;
    private final CustomerService customerService;

    public BookCopyDTOToBookCopyConverter(BookService bookService, CustomerService customerService) {
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @Override
    public BookCopy convert(BookCopyDTO bookCopyDTO) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(bookCopyDTO.getId());
        bookCopy.setDateOfBorrowing(bookCopyDTO.getDateOfBorrowing());
        bookCopy.setLibraryNum(bookCopyDTO.getLibraryNum());
        bookCopy.setBook(bookService.findOne(bookCopyDTO.getBookId()));
        if (bookCopyDTO.getCustomerId() != null) {
            bookCopy.setCustomer(customerService.findOne(bookCopyDTO.getCustomerId()));
        }
        return bookCopy;
    }

    public List<BookCopy> convert(List<BookCopyDTO> dtos) {
        return dtos.stream().map(dto -> convert(dto)).collect(Collectors.toList());
    }

}
