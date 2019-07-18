package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookDTO;
import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.service.BookService;
import com.hybridit.HybridLibrary.utils.BookDTOToBookConverter;
import com.hybridit.HybridLibrary.utils.BookToBookDTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService bookService;
    private final BookToBookDTOConverter bookToBookDTOConverter;
    private final BookDTOToBookConverter bookDTOToBookConverter;

    public BookController(BookService bookService, BookToBookDTOConverter bookToBookDTOConverter,
                          BookDTOToBookConverter bookDTOToBookConverter) {
        this.bookService = bookService;
        this.bookToBookDTOConverter = bookToBookDTOConverter;
        this.bookDTOToBookConverter = bookDTOToBookConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_MANAGER"})
    public ResponseEntity<List<BookDTO>> getAll() {
        return new ResponseEntity<>(bookToBookDTOConverter.convert(bookService.findAll()), HttpStatus.OK);
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<BookDTO> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(bookToBookDTOConverter.convert(bookService.findOne(id)), HttpStatus.OK);
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<BookDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(bookToBookDTOConverter.convert(bookService.delete(id)), HttpStatus.OK);
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        Book book = bookDTOToBookConverter.convert(bookDTO);
        bookService.save(book);
        return new ResponseEntity<>(bookToBookDTOConverter.convert(book), HttpStatus.CREATED);
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", value = "/{id}")
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO bookDTO, @PathVariable Long id) {
        Book book = bookDTOToBookConverter.convert(bookDTO);
        Book updated = bookService.update(book, id);
        return new ResponseEntity<>(bookToBookDTOConverter.convert(updated), HttpStatus.OK);
    }
}
