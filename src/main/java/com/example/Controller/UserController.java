package com.example.Controller;

import com.example.exceptions.UserException;
import com.example.model.User;
import com.example.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileByJwt(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = authService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }


}
