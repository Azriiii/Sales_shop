package com.example.Controller;

import com.example.exceptions.ProductException;
import com.example.exceptions.UserException;
import com.example.model.Rating;
import com.example.model.Review;
import com.example.model.User;
import com.example.request.ReviewRequest;
import com.example.service.AuthService;
import com.example.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    public ReviewController(ReviewService reviewService, AuthService authService) {
        this.reviewService = reviewService;
        this.authService = authService;
    }

    @GetMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = authService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReview(@PathVariable("productId") Long productId) throws UserException, ProductException {
        List<Review> reviews = reviewService.getProductReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.FOUND);
    }


}
