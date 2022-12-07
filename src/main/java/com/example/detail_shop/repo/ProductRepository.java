package com.example.Shop.repo;

import com.example.Shop.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findProductByTitleContains(String title);
}
