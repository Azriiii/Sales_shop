package com.example.Controller;

import com.example.exceptions.ProductException;
import com.example.exceptions.UserException;
import com.example.model.Rating;
import com.example.model.User;
import com.example.request.RatingRequest;
import com.example.service.AuthService;
import com.example.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {
    private final AuthService authService;
    private final RatingService ratingService;

    public RatingController(AuthService authService, RatingService ratingService) {
        this.authService = authService;
        this.ratingService = ratingService;
    }

    @GetMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = authService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req, user);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRating(@PathVariable("productId") Long productId, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = authService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.CREATED);
    }
























}
