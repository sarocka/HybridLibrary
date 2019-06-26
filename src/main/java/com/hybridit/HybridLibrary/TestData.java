package com.hybridit.HybridLibrary;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.service.BookCopyService;
import com.hybridit.HybridLibrary.service.BookService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestData {

    public final BookService bookService;
    public final BookCopyService bookCopyService;

    public TestData(BookService bookService, BookCopyService bookCopyService) {
        this.bookService = bookService;
        this.bookCopyService = bookCopyService;
    }

    @PostConstruct
    public void init() {
        Book b1 = new Book("Lagum", "145-67-892-098-76", "Prosveta");
        Book b2 = new Book("Flaubert's parrot", "145-67-892-098-79", "Geopoetika");
       /* b1.addAuthor(new Author("Svetlana Velmar Jankovic"));
        b2.addAuthor(new Author("Julian Barnes"));*/
//dodaj authorService.save

        BookCopy copy1 = new BookCopy("1s4df");
        BookCopy copy2 = new BookCopy("12rt3");
        b1.addCopy(copy1);
        b1.addCopy(copy2);
        copy1.setBook(b1);
        copy2.setBook(b1);
        bookService.save(b1);
        bookService.save(b2);
        bookCopyService.save(copy1);
        bookCopyService.save(copy2);

        bookService.save(b1);
        bookService.save(b2);

    }
}
