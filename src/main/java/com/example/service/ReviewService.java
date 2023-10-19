package com.example.service;

import com.example.exceptions.ProductException;
import com.example.model.Review;
import com.example.model.User;
import com.example.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getProductReview(Long productId) throws ProductException;

}
