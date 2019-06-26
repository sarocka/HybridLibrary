package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookCopyService;
import com.hybridit.HybridLibrary.utils.BookCopyDTOToBookCopyConverter;
import com.hybridit.HybridLibrary.utils.BookCopyToBookCopyDTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/copies")
public class BookCopyController {

    private final BookCopyService bookCopyService;
    private final BookCopyDTOToBookCopyConverter bookCopyDTOToBookCopyConverter;
    private final BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter;

    public BookCopyController(BookCopyService bookCopyService, BookCopyDTOToBookCopyConverter bookCopyDTOToBookCopyConverter,
                              BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter) {
        this.bookCopyService = bookCopyService;
        this.bookCopyDTOToBookCopyConverter= bookCopyDTOToBookCopyConverter;
        this.bookCopyToBookCopyDTOConverter= bookCopyToBookCopyDTOConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookCopyDTO>> getAll() {
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(bookCopyService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<BookCopyDTO> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(bookCopyService.findOne(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<BookCopyDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(bookCopyService.delete(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<BookCopyDTO> create(@RequestBody BookCopyDTO bookCopyDto) {
        BookCopy bookCopy=bookCopyService.save(bookCopyDTOToBookCopyConverter.convert(bookCopyDto));
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(bookCopy),HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", value = "/{id}")
    public ResponseEntity<BookCopyDTO> update(@RequestBody BookCopyDTO bookCopyDTO, @PathVariable Long id) {
        BookCopy bookCopyUpdated=bookCopyService.update(bookCopyDTOToBookCopyConverter.convert(bookCopyDTO),id);
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(bookCopyUpdated), HttpStatus.OK);
    }


}
