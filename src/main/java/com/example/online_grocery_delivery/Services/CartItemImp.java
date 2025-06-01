package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.ResponceDto;
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
    public ResponceDto addCartItem(CartDto cartDto) {
        ResponceDto responceDto = new ResponceDto();

        Product product = productRepo.findById(cartDto.getProductId()).orElse(null);
        if (product == null){
            responceDto.setCode("ERROR");
            responceDto.setMessage("Product not found");
            return responceDto;
        }

        Cart cartItem = new Cart();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartDto.getQuantity());
        cartRepo.save(cartItem);

        responceDto.setCode("SUCCESS");
        responceDto.setMessage("Cart Item added successfully");
        return  responceDto;
    }

    @Override
    public ResponceDto getAllCartItems() {
        ResponceDto responceDto = new ResponceDto();
        List<Cart> cartItem = cartRepo.findAll();

        if (cartItem.isEmpty()){
            responceDto.setCode("ERROR");
            responceDto.setMessage("No items in the cart");
            return responceDto;
        }

        List<CartDto> cartItemDto = cartItem.stream().map(item ->{
            CartDto dto = new CartDto();
            dto.setProductId(item.getProduct().getId());
            dto.setQuantity(item.getQuantity());
            return dto;
        }).collect(Collectors.toList());

        responceDto.setCode("SUCCESS");
        responceDto.setMessage("Cart items retrieved");
        responceDto.setData(cartItemDto);
        return responceDto;
    }
}
