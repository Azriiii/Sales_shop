package com.example.service;

import com.example.exceptions.CartItemException;
import com.example.exceptions.UserException;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.cartItem;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    public cartItem createCartItem(cartItem cartItem);
    public cartItem updateCartItem(Long userId,Long id,cartItem cartItem)throws CartItemException, UserException;
    public cartItem isCartItemExist(Cart cart, Product product,String size,Long userId);
    public void removeCartItem(Long userId,Long cartItemId)throws CartItemException, UserException;
    public cartItem findCartItemById(Long cartItemId)throws CartItemException, UserException;
}
