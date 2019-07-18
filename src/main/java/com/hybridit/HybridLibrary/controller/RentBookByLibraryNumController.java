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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rentByLibraryNum")
public class RentBookByLibraryNumController {

    private final BookCopyService bookCopyService;
    private final BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter;

    public RentBookByLibraryNumController(BookCopyService bookCopyService, BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter) {
        this.bookCopyService = bookCopyService;
        this.bookCopyToBookCopyDTOConverter = bookCopyToBookCopyDTOConverter;
    }

    @Secured({"LIBRARIAN", "MANAGER"})
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<BookCopyDTO> rentByLibraryNum(@RequestParam String libraryNum, @RequestParam String membershipNo) {
        BookCopy rented = bookCopyService.rentByLibraryNum(libraryNum, membershipNo);
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(rented), HttpStatus.OK);
    }
}



