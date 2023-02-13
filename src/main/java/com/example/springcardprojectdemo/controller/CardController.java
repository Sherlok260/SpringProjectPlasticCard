package com.example.springcardprojectdemo.controller;

import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.CardDto;
import com.example.springcardprojectdemo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping("/getInfo")
    @PreAuthorize(value = "hasRole('USER')")
    public HttpEntity<?> checkHaveCard() {
        ApiResponse apiResponse = cardService.getAllCards();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/newCard")
    @PreAuthorize(value = "hasRole('USER')")
    public HttpEntity<?> newCard(@RequestBody CardDto dto) {
        ApiResponse apiResponse = cardService.createCard(dto);
        return ResponseEntity.ok(apiResponse);
    }

}