package com.example.Shop.controllers;

import com.example.Shop.config.enums.RoleEnum;
import com.example.Shop.models.Customer;
import com.example.Shop.models.User;
import com.example.Shop.repo.CustomerRepository;
import com.example.Shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/registration")
    public String registration(User user, Model model) {

        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        for (ObjectError error : bindingResult.getAllErrors().stream().toList()) {
            System.out.println("error: " + error);
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        Optional<User> findUser = userRepository.findUserByLogin(user.getLogin());
        if (findUser.isPresent()) {
            bindingResult.rejectValue("login", "error.existing_login", "Данный логин уже занят");
            return "registration";
        }

        user.setRole(RoleEnum.User);
        userRepository.save(user);
        Customer customer = new Customer(0, user, null);
        customerRepository.save(customer);
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String redirectHome() {
        var roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        var role = roles.stream().map((authority) -> RoleEnum.valueOf(authority.getAuthority())).findFirst();
        if (role.isPresent()) {
            switch (role.get()) {
                case User:
                    return "redirect:/customer/products";
                case HR:
                    return "redirect:/hr/employee";
                case Manager:
                    return "redirect:/manager/categories";
            }
        }
        return "redirect:/login";
    }
}
