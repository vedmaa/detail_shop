package com.example.Shop.controllers.customer;

import com.example.Shop.config.enums.StatusEnum;
import com.example.Shop.models.*;
import com.example.Shop.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer/cart")
@PreAuthorize("hasAnyAuthority('User')")
public class CustomerCartController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    PromocodeRepository promocodeRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrederItemRepository orederItemRepository;

    @GetMapping
    public String viewCart(Principal user, Model model) {
        Customer customer = customerRepository.findByUser_Login(user.getName());
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        if (cart == null) {
            cartRepository.save(new Cart(customer, null, null));
        }
        System.out.println("total Price: " + cart.getTotalPrice());
        model.addAttribute("cart", cart);
        return "customer/cart/cart_view";
    }

    @PostMapping("/apply-code")
    public String apply(@RequestParam("code") String code, Principal user) {
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        PromoCode promoCode = promocodeRepository.findByCode(code);

        if (promoCode != null && promoCode.getActive()) {
            cart.setCode(promoCode);
            cartRepository.save(cart);
        }
        return "redirect:/customer/cart";
    }

    @PostMapping("/remove-code")
    public String apply(Principal user, Model model) {

        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        cart.setCode(null);
        cartRepository.save(cart);
        return "redirect:/customer/cart";
    }

    @PostMapping("/plus/{id}")
    public String plus(@PathVariable("id") Long itemId) {
        Optional<CartItem> item = cartItemRepository.findById(itemId);

        if (item.get().getCount() < item.get().getProduct().getCount()) {
            item.get().setCount(item.get().getCount() + 1);
        }
        cartItemRepository.save(item.get());
        return "redirect:/customer/cart";
    }

    @PostMapping("/minus/{id}")
    public String minus(@PathVariable("id") Long itemId) {
        Optional<CartItem> item = cartItemRepository.findById(itemId);

        item.get().setCount(item.get().getCount() - 1);
        if (item.get().getCount() <= 0) {
            cartItemRepository.deleteById(itemId);
        } else {
            cartItemRepository.save(item.get());
        }
        return "redirect:/customer/cart";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long itemId) {
        cartItemRepository.deleteById(itemId);
        return "redirect:/customer/cart";
    }

    @GetMapping("/order")
    public String getOrder(Principal user, Model model) {
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        System.out.println("CART total price= " + cart.getCode());
        if (cart.getSize() == 0) {
            return "redirect:/customer/cart";
        }

        model.addAttribute("cart", cart);
        Iterable<PickupPoint> points = pointRepository.findAll();

        model.addAttribute("points", points);
        return "customer/cart/order";
    }

    @PostMapping("/order")
    public String postOrder(@RequestParam("pointId") Long pointId,
                            Principal user, Model model) {
        Iterable<PickupPoint> points = pointRepository.findAll();
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        model.addAttribute("points", points);
        model.addAttribute("cart", cart);
        if (pointId == null) {
            return "customer/cart/order";
        }
        var point = pointRepository.findById(pointId).get();
        var order = new CustomOrder(
                new Date(),
                StatusEnum.Processing,
                cart.getCustomer(),
                point,
                cart.getCode(),
                List.of()
        );
        System.out.println(cart.getItems().size());
        cart.getItems().stream().peek(System.out::println);

        var orderSav = orderRepository.save(order);
        orederItemRepository.saveAll(
                cart.getItems().stream()
                        .map(cartItem -> new OrderItem(cartItem.getCount(), cartItem.getProduct(), orderSav))
                        .toList()
        );

        cart.setCode(null);
        cartItemRepository.deleteByCartId(cart.getId());

        return "redirect:/customer/orders";
    }
}
