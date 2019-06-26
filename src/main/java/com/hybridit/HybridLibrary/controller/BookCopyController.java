package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookCopyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/copies")
public class BookCopyController {

    private final BookCopyService bookCopyService;

    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookCopy>> getAll() {
        return new ResponseEntity<>(bookCopyService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<BookCopy> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(bookCopyService.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<BookCopy> delete(@PathVariable Long id) {
        return new ResponseEntity<>(bookCopyService.delete(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<BookCopy> create(@RequestBody BookCopy bookCopy) {
        return new ResponseEntity<>(bookCopyService.save(bookCopy), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", value = "/{id}")
    public ResponseEntity<BookCopy> update(@RequestBody BookCopy bookCopy, @PathVariable Long id) {
        return new ResponseEntity<>(bookCopyService.update(bookCopy, id), HttpStatus.OK);
    }


}
