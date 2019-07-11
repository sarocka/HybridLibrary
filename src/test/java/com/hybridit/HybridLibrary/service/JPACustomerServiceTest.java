package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Customer;
import com.hybridit.HybridLibrary.repository.CustomerRepository;
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
public class JPACustomerServiceTest {

    @InjectMocks
    JPACustomerService jpaCustomerService;
    @Mock
    CustomerRepository customerRepositoryMock;

    @Test
    public void findOne_existingId_customerReturned() {
        Customer customerFromDb = new Customer();

        when(customerRepositoryMock.findById(customerFromDb.getId())).thenReturn(Optional.of(customerFromDb));
        Customer customerReturned = jpaCustomerService.findOne(customerFromDb.getId());

        assertNotNull(customerReturned);
        assertEquals(customerFromDb, customerReturned);
    }

    @Test(expected = ResponseStatusException.class)
    public void findOne_nonExistingId_exceptionThrown() {

        when(customerRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaCustomerService.findOne(anyLong());
    }

    @Test
    public void findAll_customersExistingInDb_listReturned() {
        List<Customer> fromDB = new ArrayList<>();
        Customer customerFromDb = new Customer();
        customerFromDb.setId(1L);
        fromDB.add(customerFromDb);

        when(customerRepositoryMock.findAll()).thenReturn(fromDB);

        List<Customer> customers = jpaCustomerService.findAll();

        assertFalse(customers.isEmpty());
        assertEquals(fromDB.size(), customers.size());
        assertEquals(fromDB.get(0), customers.get(0));
    }

    @Test(expected = ResponseStatusException.class)
    public void findAll_noCustomersToDisplay_ExceptionThrown() {
        when(customerRepositoryMock.findAll()).thenReturn(Collections.EMPTY_LIST);
        jpaCustomerService.findAll();
    }

    @Test
    public void delete_existingIdProvided_deletedCustomerReturned() {
        Customer customerFromDb = new Customer();

        when(customerRepositoryMock.findById(customerFromDb.getId())).thenReturn(Optional.of(customerFromDb));
        Customer customerDeleted = jpaCustomerService.delete(customerFromDb.getId());

        verify(customerRepositoryMock).delete(customerFromDb);

        assertEquals(customerFromDb, customerDeleted);
    }

    @Test(expected = ResponseStatusException.class)
    public void delete_nonexistingIdProvided_exceptionThrown() {

        when(customerRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaCustomerService.delete(anyLong());
    }

    @Test
    public void update_existingIdProvided_updatedCustomerReturned() {
        Customer fromRequestBody = new Customer();
        fromRequestBody.setId(1L);
        fromRequestBody.setFirstname("Name");
        Customer fromDb = new Customer();
        fromDb.setId(1L);

        when(customerRepositoryMock.findById(anyLong())).thenReturn(Optional.of(fromDb));

        Customer updated = jpaCustomerService.update(fromRequestBody, anyLong());

        assertEquals(fromRequestBody.getFirstname(), updated.getFirstname());
    }

    @Test(expected = ResponseStatusException.class)
    public void update_nonexistingIdProvided_exceptionThrown() {

        when(customerRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        jpaCustomerService.update(new Customer(), 1L);
    }

}
