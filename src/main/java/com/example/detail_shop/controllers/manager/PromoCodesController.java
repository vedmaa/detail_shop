package com.example.Shop.controllers.manager;

import com.example.Shop.models.PromoCode;
import com.example.Shop.repo.PromocodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/manager/promocodes")
@PreAuthorize("hasAnyAuthority('Manager')")
public class PromoCodesController {
    @Autowired
    PromocodeRepository promocodeRepository;

    @GetMapping
    public String getAllPromocode(@RequestParam(value = "query", required = false) String query, Model model) {
        Iterable<PromoCode> promoCodes = promocodeRepository.findAll();
        if (query != null) {
            if (!query.isBlank()) {
                model.addAttribute("query", query);
                promoCodes = promocodeRepository.findPromoCodeByCode(query);
            }
        }
        model.addAttribute("models", promoCodes);
        return "manager/promocodes/promocode_view";
    }

    @GetMapping("/add")
    public String getAddPromoCode(PromoCode promoCode, Model model) {
        model.addAttribute("promoCode", promoCode);
        return "manager/promocodes/add";
    }

    @PostMapping("/add")
    public String addPromoCode(@ModelAttribute("promoCode") @Valid PromoCode promoCode,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/promocodes/add";
        }
        promocodeRepository.save(promoCode);
        return "redirect:/manager/promocodes";
    }

    @GetMapping("/edit/{id}")
    public String getEditPromoCode(@PathVariable("id") Long id, Model model) {
        Optional<PromoCode> promoCode = promocodeRepository.findById(id);

        if (promoCode.isEmpty()) {
            return "redirect:/manager/promocodes";
        }
        model.addAttribute("id", id);
        model.addAttribute("promoCode", promoCode.get());
        return "manager/promocodes/add";
    }

    @PostMapping("/edit/{id}")
    public String editPromoCode(@PathVariable("id") Long id,
                                @ModelAttribute("promoCode") @Valid PromoCode promoCode,
                                BindingResult bindingResult, Model model) {
        model.addAttribute("id", id);
        if (bindingResult.hasErrors()) return "manager/promocodes/add";

        promocodeRepository.save(promoCode);
        return "redirect:/manager/promocodes";
    }

    @PostMapping("/toggle/{id}")
    public String togglePromoCode(@PathVariable("id") Long id) {
        Optional<PromoCode> promoCode = promocodeRepository.findById(id);

        if (promoCode.isPresent()) {
            promoCode.get().setActive(!promoCode.get().getActive());
            promocodeRepository.save(promoCode.get());
        }
        return "redirect:/manager/promocodes";
    }

    @PostMapping("/delete/{id}")
    public String deletePromoCode(@PathVariable("id") Long id) {
        promocodeRepository.deleteById(id);
        return "redirect:/manager/promocodes";
    }
}
