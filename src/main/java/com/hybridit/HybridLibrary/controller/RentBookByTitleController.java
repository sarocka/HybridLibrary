package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookCopyService;
import com.hybridit.HybridLibrary.utils.BookCopyToBookCopyDTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/rentByTitle")
public class RentBookByTitleController {

    private final BookCopyService bookCopyService;
    private final BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter;

    public RentBookByTitleController(BookCopyService bookCopyService, BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter) {
        this.bookCopyService = bookCopyService;
        this.bookCopyToBookCopyDTOConverter=bookCopyToBookCopyDTOConverter;
    }

    @RequestMapping(method= RequestMethod.GET)
    ResponseEntity<BookCopyDTO> rentByTitle(@RequestParam String title, @RequestParam String membershipNo){
        BookCopy rented= bookCopyService.rentByBookTitle(title, membershipNo);
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(rented), HttpStatus.OK);
    }









}



