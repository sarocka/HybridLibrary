package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.service.BookService;
import com.hybridit.HybridLibrary.service.JPABookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }


    @RequestMapping(method= RequestMethod.GET)
   public ResponseEntity<List<Book>> getAll(){
            List<Book> books = bookService.findAll();

            if (books==null || books.isEmpty() ){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
    }

    @RequestMapping(method=RequestMethod.GET, value="/{id}")
    public ResponseEntity<Book> getOne(@PathVariable Long id){
        Book book = bookService.findOne(id);
        if(book==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        Book deleted = bookService.delete(id);
        if(deleted==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deleted, HttpStatus.OK);

    }

    @RequestMapping(method=RequestMethod.POST,consumes="application/json")
        public ResponseEntity<Book> create(@RequestBody Book book){
            Book created = bookService.save(book);
            if(created==null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(created, HttpStatus.CREATED);
            }

    }

    @RequestMapping(method=RequestMethod.PUT,consumes="application/json", value="/{id}")
    public ResponseEntity<Book> update(@RequestBody Book book, @PathVariable Long id){
        if(!id.equals(book.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else{
            Book updated = bookService.save(book);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }

    }



}
