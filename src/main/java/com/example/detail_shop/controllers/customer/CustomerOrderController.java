package com.example.Shop.controllers.customer;

import com.example.Shop.models.CustomOrder;
import com.example.Shop.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customer/orders")
@PreAuthorize("hasAnyAuthority('User')")
public class CustomerOrderController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping
    public String orderView(Principal user, Model model) {
        List<CustomOrder> orders = orderRepository.findByCustomerUserLogin(user.getName());
        model.addAttribute("models", orders);

        return "customer/order/order";
    }
}
