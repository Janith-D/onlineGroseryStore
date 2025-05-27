package com.example.online_grocery_delivery.Dto;

import com.example.online_grocery_delivery.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponceDto {
    private String code;
    private String message;
    private List<Product> data;
}
