package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookCopyService;
import com.hybridit.HybridLibrary.utils.BookCopyToBookCopyDTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/overdueBooks")
public class GetOverdueBooksController {

    private final BookCopyService bookCopyService;
    private final BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter;

    public GetOverdueBooksController(BookCopyService bookCopyService, BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter) {
        this.bookCopyService = bookCopyService;
        this.bookCopyToBookCopyDTOConverter = bookCopyToBookCopyDTOConverter;
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_MANAGER"})
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<BookCopyDTO>> getOverdueBooks() {
        List<BookCopy> overdueBooks = bookCopyService.getOverdueCopies();
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(overdueBooks), HttpStatus.OK);
    }
}
