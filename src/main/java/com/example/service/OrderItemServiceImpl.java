package com.example.service;

import com.example.Repository.OrderitemRepository;
import com.example.model.Orderitem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderitemRepository orderitemRepository;

    public OrderItemServiceImpl(OrderitemRepository orderitemRepository) {
        this.orderitemRepository = orderitemRepository;
    }

    @Override
    public Orderitem createOrderItem(Orderitem orderitem) {
        return orderitemRepository.save(orderitem);
    }
}
