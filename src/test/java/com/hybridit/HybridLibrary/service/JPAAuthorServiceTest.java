package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Author;
import com.hybridit.HybridLibrary.repository.AuthorRepository;
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
public class JPAAuthorServiceTest {

    @InjectMocks
    JPAAuthorService jpaAuthorService;
    @Mock
    AuthorRepository authorRepositoryMock;

    @Test
    public void findOne_existingId_authorReturned() {
        Author authorFromDb = new Author();

        when(authorRepositoryMock.findById(authorFromDb.getId())).thenReturn(Optional.of(authorFromDb));
        Author authorReturned = jpaAuthorService.findOne(authorFromDb.getId());

        assertNotNull(authorReturned);
        assertEquals(authorFromDb, authorReturned);
    }

    @Test(expected = ResponseStatusException.class)
    public void findOne_nonExistingId_execptionThrown() {

        when(authorRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaAuthorService.findOne(anyLong());
    }

    @Test
    public void findAll_authorsExistingInDb_listReturned() {
        List<Author> authorsFromDb = new ArrayList<>();
        Author authorFromDb = new Author();
        authorFromDb.setId(1L);
        authorsFromDb.add(authorFromDb);

        when(authorRepositoryMock.findAll()).thenReturn(authorsFromDb);

        List<Author> authorsReturned = jpaAuthorService.findAll();

        assertFalse(authorsReturned.isEmpty());
        assertEquals(authorsFromDb.size(), authorsReturned.size());
        assertEquals(authorsFromDb.get(0), authorsReturned.get(0));
    }

    @Test(expected = ResponseStatusException.class)
    public void findAll_noAuthorsToDisplay_ExceptionThrown() {
        when(authorRepositoryMock.findAll()).thenReturn(Collections.EMPTY_LIST);
        jpaAuthorService.findAll();
    }

    @Test
    public void delete_existingIdProvided_deletedAuthorReturned() {
        Author authorFromDb = new Author();

        when(authorRepositoryMock.findById(authorFromDb.getId())).thenReturn(Optional.of(authorFromDb));
        Author authorDeleted = jpaAuthorService.delete(authorFromDb.getId());

        verify(authorRepositoryMock).delete(authorFromDb);

        assertEquals(authorFromDb, authorDeleted);
    }

    @Test(expected = ResponseStatusException.class)
    public void delete_nonexistingIdProvided_exceptionThrown() {

        when(authorRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaAuthorService.delete(anyLong());
    }

    @Test
    public void update_existingIdProvided_updatedAuthorReturned() {
        Author fromRequestBody = new Author();
        fromRequestBody.setId(1L);
        fromRequestBody.setName("Author");
        Author fromDb = new Author();
        fromDb.setId(1L);

        when(authorRepositoryMock.findById(anyLong())).thenReturn(Optional.of(fromDb));

        Author updated = jpaAuthorService.update(fromRequestBody, anyLong());

        assertEquals(fromRequestBody.getName(), updated.getName());
    }

    @Test(expected = ResponseStatusException.class)
    public void update_nonexistingIdProvided_exceptionThrown() {

        when(authorRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaAuthorService.update(new Author(), 1L);
    }

}
