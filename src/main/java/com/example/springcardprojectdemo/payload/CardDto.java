package com.example.springcardprojectdemo.payload;

import lombok.Data;

@Data
public class CardDto {
    private String company;
    private String type;
    private Integer code;
}
