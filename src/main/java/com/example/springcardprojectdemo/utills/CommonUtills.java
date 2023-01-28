package com.example.springcardprojectdemo.utills;

import java.util.Random;

public class CommonUtills {
    public static int generateCode() {
        return new Random().nextInt((999999-100000)+1)+100000;
    }
}