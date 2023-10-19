package com.example.service;

import com.example.Repository.RatingRepository;
import com.example.Repository.ReviewRepository;
import com.example.exceptions.ProductException;
import com.example.model.Product;
import com.example.model.Review;
import com.example.model.User;
import com.example.request.ReviewRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private ProductService productService;
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setCreatedAt(LocalDateTime.now());
        review.setProduct(product);
        review.setUser(user);
        review.setReview(req.getReview());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getProductReview(Long productId) {

        return reviewRepository.getAllProductReviews(productId);
    }
}
