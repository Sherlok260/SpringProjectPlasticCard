package com.example.springcardprojectdemo.controller;

import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.RegisterDto;
import com.example.springcardprojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public HttpEntity<?> register(@RequestBody RegisterDto dto) {
        ApiResponse apiResponse = userService.register(dto);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('GUEST')")
    @PostMapping("/verify")
    public HttpEntity<?> verify(@RequestParam Long code) {
        ApiResponse apiResponse = userService.verify(code);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/getUsers")
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

}
