package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.ResponceDto;

public interface CartItemService {
    ResponceDto addCartItem(CartDto cartDto);
    ResponceDto getAllCartItems();
}
