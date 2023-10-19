package com.example.service;

import com.example.Repository.*;
import com.example.exceptions.OrderException;
import com.example.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {



    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderitemRepository orderitemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, AddressRepository addressRepository, UserRepository userRepository, CartService cartService, OrderitemRepository orderitemRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderitemRepository = orderitemRepository;
    }

    @Override
    public Order createdOrder(User user, Address shippingAddress) throws OrderException {
        Address adr = Address.builder()
                .streetAddress(shippingAddress.getStreetAddress())
                .firstName(shippingAddress.getFirstName())
                .lastName(shippingAddress.getLastName())
                .Mobile(shippingAddress.getMobile())
                .state(shippingAddress.getState())
                .city(shippingAddress.getCity())
                .user(user)
                .zipCode(shippingAddress.getZipCode()).build();

        Address address = addressRepository.save(adr);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        System.out.println("--------------------------------------CARRRRRRRRRRRRRRRRRRRRRRR----------->"+cart.getCartItems());


        List<Orderitem> orderitems = new ArrayList<>();

        for (cartItem item : cart.getCartItems()) {
            Orderitem orderitem = new Orderitem();
            orderitem.setPrice(item.getPrice());
            orderitem.setProduct(item.getProduct());
            orderitem.setQuantity(item.getQuantity());
            orderitem.setSize(item.getSize());
            orderitem.setUserId(item.getUserId());
            orderitem.setDiscountedPrice(item.getDiscountedPrice());
            Orderitem createdOrderItem = orderitemRepository.save(orderitem);

            orderitems.add(createdOrderItem);

        }
        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderitems(orderitems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscounte(cart.getDiscount());
        createdOrder.setTotalitems(cart.getTotalItem());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(createdOrder);
        for (Orderitem item : orderitems) {
            item.setOrder(savedOrder);
            orderitemRepository.save(item);
        }
        return savedOrder;
    }


    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new OrderException("order not found ! ");
        }    }

    @Override
    public List<Order> userOrderHistory(Long userId) throws OrderException {
        List<Order> orders = orderRepository.getUserOrder(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return orderRepository.save(order);
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() throws OrderException {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(order.getId());
    }
}
