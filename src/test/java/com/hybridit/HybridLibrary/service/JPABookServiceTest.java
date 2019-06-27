package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JPABookServiceTest {

    private JPABookService jpaBookService;

    private BookRepository bookRepositoryMock;
    @Before
    public void setUp() throws Exception {
        bookRepositoryMock = mock(BookRepository.class);
        jpaBookService=new JPABookService(bookRepositoryMock);

    }

    @Test
    public void findOneExistingBookInDb() {
        Book bookFromDb = new Book();
        bookFromDb.setId(4l);
        bookFromDb.setTitle("test");
        when(bookRepositoryMock.findById(3l)).thenReturn(Optional.of(bookFromDb));
        Book book = jpaBookService.findOne(3L);
        assertNotNull(book);
        assertEquals(bookFromDb, book);
    }

    @Test(expected = ResponseStatusException.class)
    public void findNonExistingBook(){
        when(bookRepositoryMock.findById(3l)).thenReturn(Optional.empty());
        jpaBookService.findOne(3L);

    }

}