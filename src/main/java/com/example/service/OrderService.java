package com.example.service;

import com.example.exceptions.OrderException;
import com.example.model.Address;
import com.example.model.Order;
import com.example.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    public Order createdOrder(User user, Address shippingAddress) throws OrderException;

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> userOrderHistory(Long userId) throws OrderException;

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders() throws OrderException;

    public void deleteOrder(Long orderId) throws OrderException;


}
