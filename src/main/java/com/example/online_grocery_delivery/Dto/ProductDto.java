package com.example.online_grocery_delivery.Dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private double price;
    private int stock;
}
