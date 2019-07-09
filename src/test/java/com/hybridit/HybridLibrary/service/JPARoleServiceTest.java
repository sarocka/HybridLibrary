package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Role;
import com.hybridit.HybridLibrary.model.User;
import com.hybridit.HybridLibrary.repository.RoleRepository;
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
public class JPARoleServiceTest {

    @InjectMocks
    JPARoleService jpaRoleService;

    @Mock
    RoleRepository roleRepositoryMock;

    @Test
    public void findOneExistingRoleInDb_roleReturned() {
        Role roleFromDb = new Role();
        roleFromDb.setId(1L);
        when(roleRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(roleRepositoryMock.getOne(anyLong())).thenReturn(roleFromDb);

        Role role = jpaRoleService.findOne(1L);
        assertEquals(roleFromDb,role);
    }

    @Test(expected = ResponseStatusException.class)
    public void findOneNonExistingRoleInDb_exceptionThrown() {
        when(roleRepositoryMock.existsById(anyLong())).thenReturn(false);
        jpaRoleService.findOne(1L);
    }

    @Test
    public void findAllExistingRolesInDb_listOfRolesReturned() {
        List<Role> rolesFromDb = new ArrayList<>();
        Role role = new Role();
        role.setId(1L);
        rolesFromDb.add(role);

        when(roleRepositoryMock.findAll()).thenReturn(rolesFromDb);
        List<Role> rolesReturned = jpaRoleService.findAll();
        assertEquals(rolesFromDb.get(0), rolesReturned.get(0));
        assertFalse(rolesReturned.isEmpty());
    }

    @Test(expected = ResponseStatusException.class)
    public void findAllNonExistingRolesInDb_execeptionThrown() {

        when(roleRepositoryMock.findAll()).thenReturn(Collections.EMPTY_LIST);
        jpaRoleService.findAll();
    }

    @Test
    public void deleteExistingRoleInDb_deletedRoleReturned() {
        Role roleFromDB = new Role();
        roleFromDB.setId(1L);

        when(roleRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(roleRepositoryMock.getOne(anyLong())).thenReturn(roleFromDB);

        Role roleDeleted = jpaRoleService.delete(1L);
        assertEquals(roleFromDB, roleDeleted);
    }

    @Test(expected = ResponseStatusException.class)
    public void deleteNonExistingRoleInDb_exceptionThrown() {

        when(roleRepositoryMock.existsById(any())).thenReturn(false);
        jpaRoleService.delete(anyLong());
    }
    @Test
    public void updateExistingRoleInDb_updatedRoleReturned() {
        Role fromReqBody= new Role();
        fromReqBody.setId(1L);
        Role roleFromDb = new Role();
        roleFromDb.setId(1L);

        when(roleRepositoryMock.findById(anyLong())).thenReturn(Optional.of(roleFromDb));

        Role roleUpdated = jpaRoleService.update(fromReqBody, anyLong());

        assertEquals(fromReqBody, roleUpdated);

    }

    @Test(expected = ResponseStatusException.class)
    public void updateNonExistingRoleInDb_exceptionThrown() {

        when(roleRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        jpaRoleService.update(new Role(),anyLong());
    }


}
