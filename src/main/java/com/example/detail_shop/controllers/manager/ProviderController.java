package com.example.Shop.controllers.manager;

import com.example.Shop.models.Product;
import com.example.Shop.models.Provider;
import com.example.Shop.repo.ProductRepository;
import com.example.Shop.repo.ProviderRepository;
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
@RequestMapping("/manager/providers")
@PreAuthorize("hasAnyAuthority('Manager')")
public class ProviderController {
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public String getAllProvider(@RequestParam(value = "query", required = false) String query, Provider provider, Model model) {
        Iterable<Provider> providers = providerRepository.findAll();

        if (query != null) {
            if (!query.isBlank()) {
                model.addAttribute("query", query);
                providers = providerRepository.findProviderByNameContains(query);
            }
        }
        model.addAttribute("provider", provider);
        model.addAttribute("models", providers);
        return "manager/provider/provider_view";
    }

    @PostMapping("")
    public String addProvider(@ModelAttribute("provider") @Valid Provider provider, BindingResult bindingResult, Model model) {
        Iterable<Provider> providers = providerRepository.findAll();
        List<Provider> providerList = new ArrayList<>();
        providers.forEach(providerList::add);

        model.addAttribute("models", providerList);

        if (bindingResult.hasErrors()) {
            return "manager/provider/provider_view";
        }
        providerRepository.save(provider);
        return "redirect:/manager/providers";
    }

    @GetMapping("/edit/{id}")
    public String editProviderGet(@PathVariable("id") Long id, Model model) {
        Optional<Provider> provider = providerRepository.findById(id);
        if (provider.isEmpty()) {
            return "redirect:/manager/providers";
        }
        model.addAttribute("provider", provider.get());
        return "manager/provider/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProvider(@ModelAttribute("provider") @Valid Provider provider,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "manager/provider/edit";
        }
        providerRepository.save(provider);
        return "redirect:/manager/providers";
    }

    @PostMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        Optional<Provider> provider = providerRepository.findById(id);

        if (!provider.get().getProducts().isEmpty()) {
            for (Product product : provider.get().getProducts()) {
                productRepository.deleteById(product.getId());
            }
        }
        providerRepository.deleteById(id);
        return "redirect:/manager/providers";
    }
}
