package com.example.Shop.repo;

import com.example.Shop.models.Cart;
import org.springframework.data.repository.CrudRepository;
public interface CartRepository extends CrudRepository<Cart, Long> {

    Cart findByCustomerUserLogin(String login);
}
