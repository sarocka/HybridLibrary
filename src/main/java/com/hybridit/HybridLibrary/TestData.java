package com.hybridit.HybridLibrary;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.service.BookService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestData {

    public final BookService bookService;

    public TestData(BookService bookService) {
        this.bookService = bookService;
    }


    @PostConstruct
    public void init() {

        Book b1 = new Book("Lagum");
        Book b2 = new Book("Flaubert's parrot");
        bookService.save(b1);
        bookService.save(b2);


    }
}
