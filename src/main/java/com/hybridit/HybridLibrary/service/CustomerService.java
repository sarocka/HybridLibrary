package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer findOne(Long id);
    List<Customer> findAll();
    Customer save(Customer customer);
    Customer delete(Long id);
    Customer update(Customer customer, Long id);
}
