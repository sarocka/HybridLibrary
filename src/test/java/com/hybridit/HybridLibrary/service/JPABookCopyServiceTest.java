package com.hybridit.HybridLibrary.service;

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
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class JPABookCopyServiceTest {

    @InjectMocks
    private JPABookCopyService jpaBookCopyService;

    @Mock
    private BookCopyRepository bookCopyRepositoryMock;

    @Test
    public void findOne_existingBookCopyIdIsProvided_bookCopyFoundInDb(){

        BookCopy copyInDb= new BookCopy();
        copyInDb.setId(1l);

        when(bookCopyRepositoryMock.findById(copyInDb.getId())).thenReturn(Optional.of(copyInDb));

       BookCopy foundCopy= jpaBookCopyService.findOne(copyInDb.getId());

       assertNotNull(foundCopy);
       assertEquals(copyInDb,foundCopy);
    }
    @Test(expected = ResponseStatusException.class)
    public void findOne_nonexistingBookCopyIdProvided_exceptionThrown(){

        when(bookCopyRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaBookCopyService.findOne(1l);
    }
    @Test
    public void findAll_booksExistingInDb_listOfBooksReturned(){
        List<BookCopy>bookCopiesInDb = new ArrayList<>();
        bookCopiesInDb.add(new BookCopy());

        when(bookCopyRepositoryMock.findAll()).thenReturn(bookCopiesInDb);
       List<BookCopy> returnedCopies= jpaBookCopyService.findAll();
       assertFalse(returnedCopies.isEmpty());
    }
    @Test(expected = ResponseStatusException.class)
    public void findAll_booksNotExistingInDb_exceptionIsThrown(){
        when(bookCopyRepositoryMock.findAll()).thenReturn(Collections.EMPTY_LIST);
        jpaBookCopyService.findAll();


    }

}
