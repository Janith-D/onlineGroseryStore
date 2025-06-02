package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.ProductDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;

public interface ProductService {
    ResponseDto<ProductDto> createProduct(ProductDto productDto);
    ResponseDto<ProductDto> getAllProducts();
}
