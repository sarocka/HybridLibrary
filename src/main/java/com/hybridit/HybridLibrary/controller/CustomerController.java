package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.model.Customer;
import com.hybridit.HybridLibrary.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Secured({"MANAGER", "LIBRARIAN"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @Secured({"MANAGER", "LIBRARIAN"})
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Customer> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findOne(id), HttpStatus.OK);
    }

   // @Secured({"MANAGER", "LIBRARIAN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.delete(id), HttpStatus.OK);
    }

   // @Secured({"MANAGER", "LIBRARIAN"})
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

   // @Secured({"MANAGER", "LIBRARIAN"})
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", value = "/{id}")
    public ResponseEntity<Customer> update(@RequestBody Customer customer, @PathVariable Long id) {
        return new ResponseEntity<>(customerService.update(customer, id), HttpStatus.OK);
    }
}
