package com.example.Shop.repo;

import com.example.Shop.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findCategoryById(Long id);
}
