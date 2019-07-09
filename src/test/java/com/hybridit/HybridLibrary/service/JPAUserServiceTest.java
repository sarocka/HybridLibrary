package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.User;
import com.hybridit.HybridLibrary.repository.UserRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JPAUserServiceTest {

    @InjectMocks
    JPAUserService jpaUserService;

    @Mock
    UserRepository userRepositoryMock;

    @Test
    public void findOneExistingUserInDb_userReturned() {
        User userFromDb = new User();
        userFromDb.setId(1L);
        userFromDb.setUsername("username");
        when(userRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(userRepositoryMock.getOne(anyLong())).thenReturn(userFromDb);

        User user = jpaUserService.findOne(1L);
        assertEquals(userFromDb, user);
    }

    @Test(expected = ResponseStatusException.class)
    public void findOneNonExistingUserInDb_exceptionThrown() {
        when(userRepositoryMock.existsById(anyLong())).thenReturn(false);
        jpaUserService.findOne(1L);
    }

    @Test
    public void findAllExistingUsersInDb_listOfUsersReturned() {
        List<User> usersFromDb = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        usersFromDb.add(user);

        when(userRepositoryMock.findAll()).thenReturn(usersFromDb);
        List<User> usersReturned = jpaUserService.findAll();
        assertEquals(usersFromDb.get(0), usersReturned.get(0));
        assertFalse(usersReturned.isEmpty());
    }

    @Test(expected = ResponseStatusException.class)
    public void findAllNonExistingUsersInDb_execeptionThrown() {

        when(userRepositoryMock.findAll()).thenReturn(Collections.EMPTY_LIST);
        jpaUserService.findAll();
    }

    @Test
    public void deleteExistingUserInDb_deletedUserReturned() {
        User userFromDb = new User();
        userFromDb.setId(1L);
        userFromDb.setUsername("username");
        when(userRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(userRepositoryMock.getOne(anyLong())).thenReturn(userFromDb);

        User userDeleted = jpaUserService.delete(1L);
        assertEquals(userFromDb, userDeleted);
    }

    @Test(expected = ResponseStatusException.class)
    public void deleteNonExistingUserInDb_execeptionThrown() {

        when(userRepositoryMock.existsById(any())).thenReturn(false);
        jpaUserService.delete(anyLong());
    }
    @Test
    public void updateExistingUserInDb_updatedUserReturned() {
        User fromReqBody= new User();
        fromReqBody.setId(1L);
        User userFromDb = new User();
        userFromDb.setId(1L);

        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(userFromDb));

        User userUpdated = jpaUserService.update(fromReqBody, anyLong());

        assertEquals(fromReqBody, userUpdated);

    }

    @Test(expected = ResponseStatusException.class)
    public void updateNonExistingUserInDb_exeptionThrown() {

        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        jpaUserService.update(new User(),anyLong());
    }


}
