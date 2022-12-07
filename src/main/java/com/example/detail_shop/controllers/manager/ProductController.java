package com.example.Shop.controllers.manager;

import com.example.Shop.models.Product;
import com.example.Shop.repo.CategoryRepository;
import com.example.Shop.repo.ProductRepository;
import com.example.Shop.repo.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/manager/products")
@PreAuthorize("hasAnyAuthority('Manager')")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProviderRepository providerRepository;

    @GetMapping
    public String viewAllProducts(@RequestParam(value = "query", required = false) String query, Model model) {
        Iterable<Product> products = productRepository.findAll();

        if (query != null) {
            if (!query.isBlank()) {
                model.addAttribute("query", query);
                products = productRepository.findProductByTitleContains(query);
            }
        }
        model.addAttribute("models", products);
        return "manager/products/products_view";
    }

    @GetMapping("/add")
    public String getAddProduct(Product product, Model model) {
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("providers", providerRepository.findAll());
        return "manager/products/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult bindingResult, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("providers", providerRepository.findAll());
        if (bindingResult.hasErrors()) {
            return "manager/products/add";
        }

        productRepository.save(product);
        return "redirect:/manager/products";
    }

    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "redirect:/manager/products";
        }

        model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("id", id);

        return "manager/products/add";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id,
                              @ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("id", id);
        if (bindingResult.hasErrors()) {
            return "manager/products/add";
        }
        productRepository.save(product);
        return "redirect:/manager/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/manager/products";
    }

}
