package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;

public interface CartItemService {
    ResponseDto addCartItem(CartDto cartDto);
    ResponseDto getAllCartItems();
}
