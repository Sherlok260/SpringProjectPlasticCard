package com.example.springcardprojectdemo.controller;

import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.LoginDto;
import com.example.springcardprojectdemo.repository.UserRepository;
import com.example.springcardprojectdemo.security.JwtProvider;
import com.example.springcardprojectdemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()));
        String token = jwtProvider.generateToken(dto.getEmail());
        System.out.println(token);

        return ResponseEntity.ok(new ApiResponse("foydalanuvchi", true, token));

    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello")
    public HttpEntity<?> hello() {

        return ResponseEntity.ok("hello");
    }

    @GetMapping("/getRoles")
    public HttpEntity<?> getRoles() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
