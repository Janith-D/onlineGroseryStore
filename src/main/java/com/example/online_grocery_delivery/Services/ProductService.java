package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.ProductDto;
import com.example.online_grocery_delivery.Dto.ResponceDto;

import java.util.List;

public interface ProductService {
    ResponceDto createProduct(ProductDto productDto);
    ResponceDto getAllProducts();
}
