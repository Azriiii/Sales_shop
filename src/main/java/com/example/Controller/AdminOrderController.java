package com.example.Controller;

import com.example.exceptions.OrderException;
import com.example.model.Order;
import com.example.model.User;
import com.example.service.AuthService;
import com.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {


    private final OrderService orderService;

    private final AuthService authService;

    public AdminOrderController(OrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler() throws OrderException {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        User user = authService.findUserProfileByJwt(jwt);
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/shipped")
    public ResponseEntity<Order> ShippedOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        User user = authService.findUserProfileByJwt(jwt);
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> DeliverOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        User user = authService.findUserProfileByJwt(jwt);
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> CancelOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        User user = authService.findUserProfileByJwt(jwt);
        Order order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) {
        try {
            User user = authService.findUserProfileByJwt(jwt);
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Order deleted successfully.");
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting order.");
        }
    }


}
