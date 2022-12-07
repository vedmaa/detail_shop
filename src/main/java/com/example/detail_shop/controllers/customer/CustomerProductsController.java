package com.example.Shop.controllers.customer;

import com.example.Shop.models.*;
import com.example.Shop.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer/products")
@PreAuthorize("hasAnyAuthority('Customer')")
public class CustomerProductsController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @GetMapping
    public String mainPage(@RequestParam(value = "query", required = false) String query,
                           @RequestParam(value = "categoryId", required = false) Long categoryId,
                           Model model) {

        model.addAttribute("categoryId", categoryId);

        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        Iterable<Product> products = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        products.forEach(productList::add);

        if (query != null) {
            if (!query.isBlank()) {
                model.addAttribute("query", query);
                productList = productList.stream().filter(
                        product -> product.getTitle().toLowerCase().contains(query.toLowerCase()) || product.getDescription().toLowerCase().contains(query.toLowerCase())).toList();
            } else if (categoryId != null) {
                productList = productList.stream().filter(product -> product.getCategory().getId().equals(categoryId)).toList();
            }
        }
        model.addAttribute("models", productList);
        return "/customer/products";
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long productId, Principal user, Model model) {
        Optional<Product> product = productRepository.findById(productId);
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        Customer customer = customerRepository.findByUser_Login(user.getName());

        if (cart == null) {
            cartRepository.save(new Cart(customer, null, null));
        }
        CartItem productInCart = cart.addProduct(product.get());
        System.out.println("Product in cart: " + productInCart);
        cartItemRepository.save(productInCart);
        return "redirect:/customer/products";
    }
}
