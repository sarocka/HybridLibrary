package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.Customer;
import com.hybridit.HybridLibrary.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JPACustomerService implements CustomerService {

    private final CustomerRepository customerRepository;

    public JPACustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findOne(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id provided does not exist");
        }
        return customerRepository.getOne(id);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers to display");
        }
        return customers;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with provided id does not exist");
        } else {
            Customer deleted = customerRepository.getOne(id);
            customerRepository.deleteById(id);
            return deleted;
        }
    }

    @Override
    public Customer update(Customer fromRequestBody, Long id) {
        return customerRepository.findById(id).map(customer -> {
            customer.setFirstname(fromRequestBody.getFirstname());
            customer.setLastname(fromRequestBody.getLastname());
            customer.setAddress(fromRequestBody.getAddress());
            customer.setPhoneNo(fromRequestBody.getPhoneNo());
            customerRepository.save(customer);
            return customer;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with provided id does not exist"));
    }
}
