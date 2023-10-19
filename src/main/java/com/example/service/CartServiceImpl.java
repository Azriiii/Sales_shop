package com.example.service;

import com.example.Repository.CartRepository;
import com.example.exceptions.ProductException;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.cartItem;
import com.example.request.AddItemRequest;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }


    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
//        cart.setDiscount(0);
//        cart.setTotalDiscountedPrice(0);
//        cart.setTotalItem(0);
//        cart.setTotalPrice(0);
        cart.setUser(user);
        System.out.println(cart);
        return cartRepository.save(cart);
    }

    @Override
    public Cart addCartItem(Long userId, AddItemRequest req) throws  ProductException {
        Cart cart = cartRepository.findCartByUserId(userId);
        Product product = productService.findProductById(req.getProductId());
        cartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

        if (isPresent == null) {
            cartItem cartItem = new cartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            cartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);


        }

        System.out.println("-----------------------------------------------------------------------------AFTER INSERTION CHECK FINDUSERCART FUNCTION----------->"+findUserCart(userId));

        return cart;
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;
        System.out.println("--------------------------------------FUNCTION----------->"+cart.getCartItems());

        for (cartItem cartItem : cart.getCartItems()) {
            totalPrice =totalPrice+ cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice+cartItem.getDiscountedPrice();
            totalItem = totalItem+cartItem.getQuantity();

        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
