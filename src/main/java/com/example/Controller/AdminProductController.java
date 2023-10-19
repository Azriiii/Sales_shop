package com.example.Controller;

import com.example.exceptions.OrderException;
import com.example.exceptions.ProductException;
import com.example.model.Product;
import com.example.request.CreateProductRequest;
import com.example.service.ApiResponse;
import com.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")

public class AdminProductController {


    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request, @RequestHeader("Authorization") String jwt) {
        Product p =productService.createProduct(request);
        return new ResponseEntity<>(p,HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createProducts(@RequestBody CreateProductRequest[] request, @RequestHeader("Authorization") String jwt) {
        for (CreateProductRequest product : request) {
            productService.createProduct(product);
        }
        ApiResponse res = new ApiResponse();
        res.setMessage("Order created succefully");
        res.setStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(res, res.getStatus());
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) {
        try {
            productService.deleteProduct(productId);
            ApiResponse res = new ApiResponse();
            res.setMessage("Order deleted succefully");
            res.setStatus(HttpStatus.ACCEPTED);
            return new ResponseEntity<>(res, res.getStatus());
        } catch (ProductException e) {
            ApiResponse res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting order");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProductssHandler() throws OrderException {
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable("productId") Long productId) throws OrderException, ProductException {
        Product product = productService.updateProduct(productId, req);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }


}



