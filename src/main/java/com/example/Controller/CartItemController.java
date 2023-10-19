package com.example.Controller;

import com.example.exceptions.CartItemException;
import com.example.exceptions.ProductException;
import com.example.exceptions.UserException;
import com.example.model.Cart;
import com.example.model.User;
import com.example.model.cartItem;
import com.example.request.AddItemRequest;
import com.example.service.ApiResponse;
import com.example.service.AuthService;
import com.example.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartitem")
public class CartItemController {


    private final CartItemService cartItemService;
    private final AuthService authService;

    public CartItemController(CartItemService cartItemService, AuthService authService) {
        this.cartItemService = cartItemService;
        this.authService = authService;
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<cartItem> deleteCartItem(@PathVariable("cartItemId")Long cartItemId,@RequestBody cartItem cartItem ,@RequestHeader("Authorization") String jwt) throws CartItemException, ProductException, UserException {
        User user = authService.findUserProfileByJwt(jwt);
       cartItemService.removeCartItem(user.getId(), cartItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<cartItem> getCarteItems(@PathVariable("cartItemId")Long cartItemId) throws CartItemException, ProductException, UserException {
        cartItem cartItem = cartItemService.findCartItemById(cartItemId);
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }
    // get all carte items
    @PostMapping("/{cartItemId}")
    public ResponseEntity<cartItem> updateCartItem(@PathVariable("cartItemId")Long cartItemId,@RequestBody cartItem cartItem ,@RequestHeader("Authorization") String jwt) throws CartItemException, ProductException, UserException {
        User user = authService.findUserProfileByJwt(jwt);
        cartItem  item = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }
}
