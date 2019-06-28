package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JPABookServiceTest {

    @InjectMocks
    private JPABookService jpaBookService;

    @Mock
    private BookRepository bookRepositoryMock;

    @Test
    public void findOneExistingBookInDb() {
        Book bookFromDb = new Book();
        bookFromDb.setId(4l);
        bookFromDb.setTitle("test");
        when(bookRepositoryMock.findById(3l)).thenReturn(Optional.of(bookFromDb));
        Book bookFound = jpaBookService.findOne(3L);
        assertNotNull(bookFound);
        assertEquals(bookFromDb, bookFound);
    }

    @Test(expected = ResponseStatusException.class)
    public void findNonExistingBook() {
        when(bookRepositoryMock.findById(3l)).thenReturn(Optional.empty());
        jpaBookService.findOne(3L);
    }

    @Test
    public void findAll_booksExistingInDb_listOfBooksReturned() {
        List<Book> booksInDb = new ArrayList<>();
        booksInDb.add(new Book());

        when(bookRepositoryMock.findAll()).thenReturn(booksInDb);
        List<Book> booksReturned = jpaBookService.findAll();
        assertFalse(booksReturned.isEmpty());
    }

    @Test(expected = ResponseStatusException.class)
    public void findAll_booksNotExistingInDb_exceptionIsThrown() {
        when(bookRepositoryMock.findAll()).thenReturn(Collections.EMPTY_LIST);

        jpaBookService.findAll();
    }

    @Test
    public void delete_existingBookInDb_deletedBookReturned() {
        Book bookFromDb = new Book();
        bookFromDb.setId(1L);
        bookFromDb.setTitle("Title");

        when(bookRepositoryMock.findById(bookFromDb.getId())).thenReturn(Optional.of(bookFromDb));
        Book deletedBook = jpaBookService.delete(1L);

        verify(bookRepositoryMock).delete(bookFromDb);
        assertEquals(bookFromDb.getTitle(), deletedBook.getTitle());
    }

    @Test(expected = ResponseStatusException.class)
    public void delete_nonexistingBookInDb_throwsException() {
        when(bookRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        jpaBookService.delete(1l);
    }

    @Test
    public void update_existingBookInDb_updatedBookReturned() {
        Book bookForUpdate = new Book();
        bookForUpdate.setTitle("updated title");

        when(bookRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(bookRepositoryMock.save(bookForUpdate)).thenReturn(bookForUpdate);

        Book updatedBook = jpaBookService.update(bookForUpdate, 1l);

        assertEquals(bookForUpdate.getTitle(), updatedBook.getTitle());
    }

    @Test(expected = ResponseStatusException.class)
    public void update_nonExistingBookInDb_exceptionThrown() {

        when(bookRepositoryMock.existsById(anyLong())).thenReturn(false);
        jpaBookService.update(new Book(), 1l);
    }

}