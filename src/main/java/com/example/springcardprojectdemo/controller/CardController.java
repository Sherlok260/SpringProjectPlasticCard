package com.example.springcardprojectdemo.controller;

import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.CardDto;
import com.example.springcardprojectdemo.payload.TransferDto;
import com.example.springcardprojectdemo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
@CrossOrigin(origins = "*")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/create")
    @PreAuthorize(value = "hasRole('USER')")
    public HttpEntity<?> createCard(@RequestBody CardDto dto) {
        ApiResponse apiResponse = cardService.createCard(dto);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/transfer")
    @PreAuthorize(value = "hasRole('USER')")
    public HttpEntity<?> transferMoney(@RequestBody TransferDto dto) {
        ApiResponse apiResponse = cardService.v_transfer(dto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getInfo")
    @PreAuthorize(value = "hasRole('USER')")
    public HttpEntity<?> checkHaveCard() {
        ApiResponse apiResponse = cardService.getAllCards();
        return ResponseEntity.ok(apiResponse);
    }

}