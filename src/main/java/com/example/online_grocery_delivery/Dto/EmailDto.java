package com.example.online_grocery_delivery.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;
}
