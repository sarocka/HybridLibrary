package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Author;
import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import com.hybridit.HybridLibrary.repository.BookCopyRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JPABookCopyServiceTest {

    @InjectMocks
    private JPABookCopyService jpaBookCopyService;

    @Mock
    private BookCopyRepository bookCopyRepositoryMock;

    @Test
    public void findOne_existingBookCopyIdIsProvided_bookCopyFoundInDb() {
        BookCopy copyInDb = new BookCopy();
        copyInDb.setId(1l);

        when(bookCopyRepositoryMock.findById(copyInDb.getId())).thenReturn(Optional.of(copyInDb));

        BookCopy foundCopy = jpaBookCopyService.findOne(copyInDb.getId());

        assertNotNull(foundCopy);
        assertEquals(copyInDb, foundCopy);
    }

    @Test(expected = ResponseStatusException.class)
    public void findOne_nonexistingBookCopyIdProvided_exceptionThrown() {

        when(bookCopyRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaBookCopyService.findOne(1l);
    }

    @Test
    public void findAll_bookCopiesExistingInDb_listOfBookCopiesReturned() {
        List<BookCopy> bookCopiesInDb = new ArrayList<>();
        BookCopy copyInDb= new BookCopy();
        copyInDb.setId(1l);
        copyInDb.setLibraryNum("abc");
        Book book = new Book();
        copyInDb.setBook(book);
        copyInDb.getBook().setId(1L);
        bookCopiesInDb.add(copyInDb);

        when(bookCopyRepositoryMock.findAll()).thenReturn(bookCopiesInDb);

        List<BookCopy> returnedCopies = jpaBookCopyService.findAll();
        assertFalse(returnedCopies.isEmpty());

        assertEquals(bookCopiesInDb.size(),returnedCopies.size());
        assertEquals(bookCopiesInDb.get(0),returnedCopies.get(0));
    }

    @Test(expected = ResponseStatusException.class)
    public void findAll_booksNotExistingInDb_exceptionIsThrown() {
        when(bookCopyRepositoryMock.findAll()).thenReturn(Collections.EMPTY_LIST);
        jpaBookCopyService.findAll();
    }

    @Test
    public void delete_existingBookCopy_DeletedBookReturned() {
        BookCopy copyInDb = new BookCopy();
        copyInDb.setId(1L);
        copyInDb.setLibraryNum("1abc");

        when(bookCopyRepositoryMock.findById(copyInDb.getId())).thenReturn(Optional.of(copyInDb));

        BookCopy deletedCopy = jpaBookCopyService.delete(copyInDb.getId());
        verify(bookCopyRepositoryMock).delete(copyInDb);
        assertEquals(copyInDb.getLibraryNum(), deletedCopy.getLibraryNum());
    }

    @Test(expected = ResponseStatusException.class)
    public void delete_nonExistingBookCopy_exceptionThrown() {

        when(bookCopyRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaBookCopyService.delete(anyLong());
    }

    @Test
    public void update_existingBookCopy_updatedBookReturned() {
        Book book = new Book();
        book.setId(1L);
        BookCopy fromRequestBody = new BookCopy();
        fromRequestBody.setId(1L);
        fromRequestBody.setBook(book);

        BookCopy fromDb = new BookCopy();
        fromDb.setId(1L);
        fromDb.setBook(book);

        when(bookCopyRepositoryMock.findById(anyLong())).thenReturn(Optional.of(fromDb));

        BookCopy updated = jpaBookCopyService.update(fromRequestBody, anyLong());

        assertEquals(fromRequestBody.getLibraryNum(), updated.getLibraryNum());
    }

    @Test(expected = ResponseStatusException.class)
    public void update_nonExistingBookCopy_exceptionThrown() {
        when(bookCopyRepositoryMock.findById((anyLong()))).thenReturn(Optional.empty());
        jpaBookCopyService.update(new BookCopy(), 1L);
    }

}
