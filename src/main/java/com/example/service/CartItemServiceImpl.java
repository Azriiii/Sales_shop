package com.example.service;

import com.example.Repository.CartItemRepository;
import com.example.exceptions.CartItemException;
import com.example.exceptions.UserException;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.cartItem;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final AuthService authService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, AuthService authService) {
        this.cartItemRepository = cartItemRepository;
        this.authService = authService;
    }

    @Override
    public cartItem createCartItem(cartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setProduct(cartItem.getProduct());
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        cartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public cartItem updateCartItem(Long userId, Long id, cartItem cartItem) throws CartItemException, UserException {
        cartItem item = findCartItemById(id);
        User user = authService.findUserById(item.getUserId());
        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public cartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        cartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);

        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        cartItem cartItem = findCartItemById(cartItemId);
        User user = authService.findUserById(cartItem.getUserId());
        User reqUser = authService.findUserById(userId);
        if (user.getId().equals(reqUser.getId())) {
            cartItemRepository.delete(cartItem);
        } else {
            throw new UserException("you can't remove another user");
        }


    }

    @Override
    public cartItem findCartItemById(Long cartItemId) throws CartItemException, UserException {
        Optional<cartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("cartitem not found" + cartItemId);
    }
}
