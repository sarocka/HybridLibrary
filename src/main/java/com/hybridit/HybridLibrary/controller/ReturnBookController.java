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

import java.util.List;

@RestController
@RequestMapping(value="/api/return")
public class ReturnBookController {

    private final BookCopyService bookCopyService;
    private final BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter;

    public ReturnBookController(BookCopyService bookCopyService, BookCopyToBookCopyDTOConverter bookCopyToBookCopyDTOConverter) {
        this.bookCopyService = bookCopyService;
        this.bookCopyToBookCopyDTOConverter=bookCopyToBookCopyDTOConverter;
    }

    @RequestMapping(method= RequestMethod.GET)
    ResponseEntity<BookCopyDTO> returnCopy(@RequestParam String libraryNum){
        BookCopy returned= bookCopyService.returnCopy(libraryNum);
        return new ResponseEntity<>(bookCopyToBookCopyDTOConverter.convert(returned), HttpStatus.OK);
    }


}
