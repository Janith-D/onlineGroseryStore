package com.example.online_grocery_delivery.Dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime time;
    private String customerName;
    private double totalPrice;
    private List<CartDto> items;
}
