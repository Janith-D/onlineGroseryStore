package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;
import com.example.online_grocery_delivery.Entity.Cart;
import com.example.online_grocery_delivery.Entity.Product;
import com.example.online_grocery_delivery.Repo.CartRepo;
import com.example.online_grocery_delivery.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemImp implements CartItemService{

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;
    @Override
    public ResponseDto addCartItem(CartDto cartDto) {
        ResponseDto responseDto = new ResponseDto();

        Product product = productRepo.findById(cartDto.getProductId()).orElse(null);
        if (product == null){
            responseDto.setCode("ERROR");
            responseDto.setMessage("Product not found");
            return responseDto;
        }

        Cart cartItem = new Cart();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartDto.getQuantity());
        cartRepo.save(cartItem);

        responseDto.setCode("SUCCESS");
        responseDto.setMessage("Cart Item added successfully");
        return responseDto;
    }

    @Override
    public ResponseDto getAllCartItems() {
        ResponseDto responseDto = new ResponseDto();
        List<Cart> cartItem = cartRepo.findAll();

        if (cartItem.isEmpty()){
            responseDto.setCode("ERROR");
            responseDto.setMessage("No items in the cart");
            return responseDto;
        }

        List<CartDto> cartItemDto = cartItem.stream().map(item ->{
            CartDto dto = new CartDto();
            dto.setProductId(item.getProduct().getId());
            dto.setQuantity(item.getQuantity());
            return dto;
        }).collect(Collectors.toList());

        responseDto.setCode("SUCCESS");
        responseDto.setMessage("Cart items retrieved");
        responseDto.setData(cartItemDto);
        return responseDto;
    }
}
