package com.example.Controller;

import com.example.exceptions.OrderException;
import com.example.exceptions.UserException;
import com.example.model.Address;
import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.User;
import com.example.service.AuthService;
import com.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")


public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    public OrderController(OrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws OrderException {
        User user = authService.findUserProfileByJwt(jwt);
        Order order = orderService.createdOrder(user, shippingAddress);
        System.out.println("order" + order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws OrderException {
        User user = authService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.FOUND);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderById(@RequestHeader("Authorization") String jwt, @PathVariable("orderId") Long orderId) throws UserException, OrderException {
        User user = authService.findUserProfileByJwt(jwt);
        Order orders = orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders, HttpStatus.FOUND);

    }


}
