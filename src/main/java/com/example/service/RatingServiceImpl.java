package com.example.service;

import com.example.Repository.RatingRepository;
import com.example.exceptions.ProductException;
import com.example.model.Product;
import com.example.model.Rating;
import com.example.model.User;
import com.example.request.RatingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setUser(user);
        rating.setProduct(product);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);


    }

    @Override
    public List<Rating> getProductRating(Long productId) throws ProductException {
//        Product product = productService.findProductById(productId);
//        product.getRatings();
        return ratingRepository.getAllProductRatings(productId);
    }
}
