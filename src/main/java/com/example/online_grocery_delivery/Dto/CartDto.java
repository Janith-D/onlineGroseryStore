package com.example.online_grocery_delivery.Dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long productId;
    private int quantity;
}
