package com.example.online_grocery_delivery.Dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private String code;
    private String message;
    private  T data;
    private java.util.List<T> dataList;
}
