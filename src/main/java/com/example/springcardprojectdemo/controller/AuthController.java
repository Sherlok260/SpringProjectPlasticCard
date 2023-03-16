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
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/f_password")
    public HttpEntity<?> forget_password(@RequestParam String email) {
        ApiResponse apiResponse = authService.f_password(email);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/v_f_password")
    @PreAuthorize(value = "hasRole('USER')")
    public HttpEntity<?> v_forget_password(@RequestParam int v_code) {
        ApiResponse apiResponse = authService.v_f_password(v_code);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/new_password")
    @PreAuthorize(value = "hasRole('USER')")
    public HttpEntity<?> new_password(@RequestParam String password) {
        ApiResponse apiResponse = authService.set_new_password(password);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello")
    public HttpEntity<?> hello() {
        return ResponseEntity.ok("hello");
    }

}
