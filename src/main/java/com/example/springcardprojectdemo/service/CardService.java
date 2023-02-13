package com.example.springcardprojectdemo.service;

import com.example.springcardprojectdemo.entity.Card;
import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.CardDto;
import com.example.springcardprojectdemo.repository.CardRepository;
import com.example.springcardprojectdemo.security.JwtFilter;
import com.example.springcardprojectdemo.utills.CommonUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    public ApiResponse getAllCards() {
        String email = JwtFilter.getEmailWithToken;
        List<Card> cards = cardRepository.getAllByUser_email(email);
        if (cards == null) {
            return new ApiResponse("null", false);
        } return new ApiResponse("Cards", true, cards);
    }

    public ApiResponse createCard(CardDto dto) {
        Card newCard = new Card();
        newCard.setCompany(dto.getCompany());
        newCard.setType(dto.getType());
        newCard.setCode(dto.getCode());
        newCard.setExp_date("01/27");
        newCard.setUser_email(JwtFilter.getEmailWithToken);
        newCard.setBalance(dto.getType().equals("Visa")?100:1000000);
        newCard.setUnit(dto.getType().equals("Visa")?"$":"uzs");
        newCard.setNumber(checkCardNumber());
        try {
            cardRepository.save(newCard);
            return new ApiResponse("success", true);
        } catch (Exception e) {
            return new ApiResponse("failed", false);
        }
    }

    public String checkCardNumber() {
        Card byNumber = null;
        int number = 0;
        do {
            number = CommonUtills.generateNumber();
            byNumber = cardRepository.findByNumber(String.valueOf(number));
        } while (byNumber != null);
        return "8600312935" + number;
    }


}
