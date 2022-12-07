package com.example.Shop.controllers.manager;

import com.example.Shop.models.Category;
import com.example.Shop.models.Product;
import com.example.Shop.repo.CategoryRepository;
import com.example.Shop.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager/categories")
@PreAuthorize("hasAnyAuthority('Manager')")
public class CategoriesController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public String getAllCategories(@RequestParam(value = "query", required = false) String query, Category category1, Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<Category>();
        categories.forEach(categoryList::add);
        if (query != null) {
            if (!query.isBlank()) {
                model.addAttribute("query", query);

                categoryList = categoryList.stream().filter((category -> category.getCategory_name().toLowerCase().contains((query.toLowerCase())))).toList();
            }
        }
        model.addAttribute("category", category1);
        model.addAttribute("models", categoryList);
        return "manager/categories/category_view";
    }

    @PostMapping("")
    public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult, Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<Category>();
        categories.forEach(categoryList::add);

        model.addAttribute("models", categoryList);

        if (!categoryList.stream().filter(category1 -> category1.getCategory_name().equals(category.getCategory_name())).toList().isEmpty()) {

            bindingResult.rejectValue("category_name", "error.existing_category", "Данная категория уже существует");
            return "manager/categories/category_view";
        }

        if (bindingResult.hasErrors()) {
            return "manager/categories/category_view";
        }

        categoryRepository.save(category);
        return "redirect:/manager/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryGet(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return "redirect:/manager/categories";
        }
        model.addAttribute("category", category.get());
        return "manager/categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@ModelAttribute("category") @Valid Category category,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/categories/edit";
        }
        categoryRepository.save(category);
        return "redirect:/manager/categories";
    }

    @PostMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (!category.get().getProducts().isEmpty()) {
            for (Product product : category.get().getProducts()) {
                productRepository.deleteById(product.getId());
            }
        }
        categoryRepository.deleteById(id);
        return "redirect:/manager/categories";
    }
}
