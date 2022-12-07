package com.example.Shop.repo;

import com.example.Shop.models.CustomOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<CustomOrder,Long> {
    List<CustomOrder> findByCustomerUserLogin(String login);
}
