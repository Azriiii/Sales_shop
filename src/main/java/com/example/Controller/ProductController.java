package com.example.Controller;

import com.example.exceptions.ProductException;
import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/prodcut/id/{productId}")
    public ResponseEntity<Product> findById(@PathVariable Long productId) throws ProductException {
        Product p = productService.findProductById(productId);
        return new ResponseEntity<>(p, HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String category) throws ProductException {
        List<Product>products = productService.findProductByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductBycategoryHandler(@RequestParam String category, @RequestParam List<String> color, @RequestParam List<String> size, @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        System.out.println("complete products ");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}
