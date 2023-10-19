package com.example.service;

import com.example.exceptions.CartItemException;
import com.example.exceptions.ProductException;
import com.example.exceptions.UserException;
import com.example.model.Cart;
import com.example.model.User;
import com.example.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public interface CartService {


    public Cart createCart(User user)throws UserException;
    public Cart addCartItem(Long userId, AddItemRequest req) throws UserException, ProductException;
    public Cart findUserCart(Long userId);


}
