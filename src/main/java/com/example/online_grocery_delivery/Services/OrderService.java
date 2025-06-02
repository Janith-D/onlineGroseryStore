package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.OrderDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;

public interface OrderService {
    ResponseDto<?> placeOrder();
    ResponseDto<OrderDto> getOrderById(Long oderId);
}
