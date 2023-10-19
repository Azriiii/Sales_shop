package com.example.service;


import com.example.Repository.CartRepository;
import com.example.Repository.UserRepository;
import com.example.config.JwtProvider;
import com.example.exceptions.UserException;
import com.example.model.Cart;
import com.example.model.Role;
import com.example.model.User;
import com.example.request.LoginRequest;
import com.example.request.registerRequest;
import com.example.response.AuthResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtService;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtProvider jwtService, AuthenticationManager authenticationManager, @Lazy CartService cartService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.cartService = cartService;
    }

    public AuthResponse register(registerRequest request) throws UserException {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new DataIntegrityViolationException("User with the same email already exists");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        cartService.createCart(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User findUserProfileByJwt(String jwt) {
        String jwtToken = jwt.split(" ")[1].trim();
        String email = jwtService.extractUsername(jwtToken);
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findUserById(Long userId) {
        return repository.findUserById(userId);
    }


    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}