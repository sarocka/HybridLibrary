package com.hybridit.HybridLibrary.repository;

import com.hybridit.HybridLibrary.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByMembershipNo(String membershipNo);
}
