package com.example.online_grocery_delivery.Controller;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.ProductDto;
import com.example.online_grocery_delivery.Dto.ResponceDto;
import com.example.online_grocery_delivery.Services.CartItemImp;
import com.example.online_grocery_delivery.Services.ProductImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/grocerydelivery/controller/")
public class Controller {

    @Autowired
    private ProductImp productImp;

    @Autowired
    private CartItemImp cartItemImp;

    @PostMapping("createProduct")
    public ResponceDto AddProduct(@RequestBody ProductDto productDto){
        return productImp.createProduct(productDto);
    }

    @GetMapping("getAllProduct")
    public ResponceDto getAllProduct(){
        return productImp.getAllProducts();
    }

    @PostMapping("addCartItems")
    public ResponceDto addCartItem(@RequestBody CartDto cartDto){
        return cartItemImp.addCartItem(cartDto);
    }

    @GetMapping("getCartItems")
    public ResponceDto getAllCartItem(){
        return cartItemImp.getAllCartItems();
    }
}
