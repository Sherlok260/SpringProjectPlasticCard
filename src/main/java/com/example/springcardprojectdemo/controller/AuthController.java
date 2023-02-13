package com.example.springcardprojectdemo.controller;

import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.LoginDto;
import com.example.springcardprojectdemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto dto) {
        ApiResponse apiResponse = authService.login(dto);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello")
    public HttpEntity<?> hello() {
        return ResponseEntity.ok("hello");
    }

}
