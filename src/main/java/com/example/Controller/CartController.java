package com.example.Controller;

import com.example.Repository.CartItemRepository;
import com.example.exceptions.ProductException;
import com.example.exceptions.UserException;
import com.example.model.Cart;
import com.example.model.User;
import com.example.request.AddItemRequest;
import com.example.service.ApiResponse;
import com.example.service.AuthService;
import com.example.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final AuthService authService;

    public CartController(CartService cartService, AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }


    @GetMapping("/")
    public ResponseEntity<Cart> findUserCartItem(@RequestHeader("Authorization") String jwt) {
        User user = authService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = authService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);

        ApiResponse api = new ApiResponse<>();
        api.setMessage("item added to cart");
        api.setStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(api, api.getStatus());
    }
}
