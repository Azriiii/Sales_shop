package com.example.service;

import com.example.Repository.CategoryRepository;
import com.example.Repository.ProductRepository;
import com.example.exceptions.ProductException;
import com.example.model.Category;
import com.example.model.Product;
import com.example.request.CreateProductRequest;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.List.of;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());


        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());

        if (secondLevel == null) {
            Category sencondLevelCategory = new Category();
            sencondLevelCategory.setName(req.getSecondLevelCategory());
            sencondLevelCategory.setLevel(2);
            sencondLevelCategory.setParentCategory(topLevel);

            secondLevel = categoryRepository.save(sencondLevelCategory);
        }
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);
            categoryRepository.save(thirdLevelCategory);
        }


        var product = Product.builder().title(req.getTitle()).color(req.getColor()).description(req.getDescription())
                .discountedPersent(req.getDiscountPersent()).discountedPrice(req.getDiscountedPrice())
                .imageUrl(req.getImageUrl()).brand(req.getBrand()).price(req.getPrice()).sizes(req.getSize())
                .quantity(req.getQuantity()).category(thirdLevel).createdAt(LocalDateTime.now()).build();
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted!";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if (req.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
            product.setPrice(req.getPrice());
            product.setTitle(req.getTitle());
            product.setCategory(req.getCategory());
            product.setColor(req.getColor());
            product.setDescription(req.getDescription());
            product.setRatings(req.getRatings());
            product.setReviews(req.getReviews());
            product.setSizes(req.getSizes());
            product.setDiscountedPrice(req.getDiscountedPrice());
            product.setDiscountedPersent(req.getDiscountedPersent());
            product.setImageUrl(req.getImageUrl());
            product.setNumRatings(req.getNumRatings());
            product.setBrand(req.getBrand());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("Product does not found with id- " + productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return productRepository.findProductByCategory(category);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sort)); // Specify the sorting criteria here

        List<Product> products = productRepository.filterProduct(category, minPrice, maxPrice, minDiscount, sort);

        if (!colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);

        Page<Product> filterProducts = new PageImpl<>(pageContent, pageable, products.size());

        return filterProducts;
    }

//    @Override
//    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
//        Page pageable = (Page) PageRequest.of(pageNumber, pageSize);
//        List<Product> products = productRepository.filterProduct(category, minPrice, maxPrice, minDiscount, sort);
//        if (!colors.isEmpty()) {
//            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
//
//        }
//        if (stock != null) {
//            if (stock.equals("in_stock")) {
//                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
//            } else if (stock.equals("out_of_stoc")) {
//                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
//            }
//
//        }
//        // error
//        int startIndex = (int) pageable.getTotalPages();
//        int endIndex = Math.min(startIndex + pageable.getSize(), products.size());
//        List<Product> pageContent = products.subList(startIndex, endIndex);
//        Page<Product> filterProducts = new PageImpl<>(pageContent, (Pageable) pageable, products.size());
//
//        return filterProducts;
//    }
}
