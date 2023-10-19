package com.example.service;

import com.example.exceptions.OrderException;
import com.example.model.Orderitem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    public Orderitem createOrderItem(Orderitem orderitem) throws OrderException;
}
