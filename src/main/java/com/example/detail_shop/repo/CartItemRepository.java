package com.example.Shop.repo;

import com.example.Shop.models.CartItem;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    @Transactional
    Long deleteByCartId(Long id);
}
