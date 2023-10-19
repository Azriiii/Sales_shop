package com.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Long timestamp;
    private HttpStatus status;
    private String message;
    private T data;

    public ApiResponse(Long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = null;
    }

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }
    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;

    }

}