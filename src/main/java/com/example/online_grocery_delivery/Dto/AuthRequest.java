package com.example.online_grocery_delivery.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthRequest {
    private String userName;
    private String password;
}


