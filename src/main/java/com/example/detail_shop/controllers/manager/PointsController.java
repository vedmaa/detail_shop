package com.example.Shop.controllers.manager;

import com.example.Shop.models.PickupPoint;
import com.example.Shop.repo.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/manager/points")
@PreAuthorize("hasAnyAuthority('Manager')")
public class PointsController {

    @Autowired
    PointRepository pointRepository;

    @GetMapping
    public String getAllPoints(@RequestParam(value = "query", required = false) String query, PickupPoint pickupPoint, Model model) {
        Iterable<PickupPoint> pickupPoints = pointRepository.findAll();
        if (query != null) {
            if (!query.isBlank()) {
                model.addAttribute("query", query);
                pickupPoints = pointRepository.findByAddressContainsIgnoreCase(query);
            }
        }
        model.addAttribute("models", pickupPoints);
        return "manager/points/points_view";
    }

    @GetMapping("/add")
    public String addGet(PickupPoint point, Model model) {
        model.addAttribute("point", point);
        return "manager/points/add";
    }

    @PostMapping("/add")
    public String addPoint(@ModelAttribute("point") @Valid PickupPoint point,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "manager/points/add";
        }
        pointRepository.save(point);
        return "redirect:/manager/points";
    }

    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        Optional<PickupPoint> point = pointRepository.findById(id);
        if (point.isEmpty()) {
            return "redirect:/manager/points";
        }
        model.addAttribute("point", point.get());
        return "/manager/points/add";
    }

    @PostMapping("/edit/{id}")
    public String editPoint(@ModelAttribute("point") @Valid PickupPoint point,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/manager/points/add";
        }
        pointRepository.save(point);
        return "redirect:/manager/points";
    }

    @PostMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        pointRepository.deleteById(id);
        return "redirect:/manager/points";
    }
}
