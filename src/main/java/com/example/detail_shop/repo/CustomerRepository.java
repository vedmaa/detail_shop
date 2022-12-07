package com.example.Shop.repo;

import com.example.Shop.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findCustomerById(Long id);
    Customer findByUser_Login(String login);

}
