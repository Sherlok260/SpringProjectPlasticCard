package com.example.springcardprojectdemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ApiResponse {
    private String message;
    private boolean success;
    private Object object;
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }
}
