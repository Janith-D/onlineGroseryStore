package com.example.online_grocery_delivery.Controller;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.OrderDto;
import com.example.online_grocery_delivery.Dto.ProductDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;
import com.example.online_grocery_delivery.Entity.Orders;
import com.example.online_grocery_delivery.Services.CartItemImp;
import com.example.online_grocery_delivery.Services.OrderImp;
import com.example.online_grocery_delivery.Services.OrderService;
import com.example.online_grocery_delivery.Services.ProductImp;
import com.example.online_grocery_delivery.Utill.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/grocerydelivery/controller/")
public class Controller {

    @Autowired
    private ProductImp productImp;

    @Autowired
    private CartItemImp cartItemImp;

    @Autowired
    private OrderImp orderImp;

    @PostMapping("createProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto AddProduct(@RequestBody ProductDto productDto){
        return productImp.createProduct(productDto);
    }

    @GetMapping("getAllProduct")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto getAllProduct(){
        return productImp.getAllProducts();
    }

    @PostMapping("addCartItems")
    @PreAuthorize("hasRole('USER')")
    public ResponseDto addCartItem(@RequestBody CartDto cartDto){
        return cartItemImp.addCartItem(cartDto);
    }

    @GetMapping("getCartItems")
    @PreAuthorize("hasRole('USER')")
    public ResponseDto getAllCartItem(){
        return cartItemImp.getAllCartItems();
    }
    @PostMapping("placeOrder")
    @PreAuthorize("hasRole('USER')")
    public ResponseDto<?> placeOrder(){
        return orderImp.placeOrder();
    }

    @GetMapping("orders/{orderId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseDto<OrderDto> getOrderById(@PathVariable Long orderId){
        return orderImp.getOrderById(orderId);
    }

    @GetMapping("orders/{orderId}/invoice")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseDto<byte[]> downloadInvoice(@PathVariable long orderId) throws IOException {
        ResponseDto<OrderDto> orders = orderImp.getOrderById(orderId);

        byte[] invoicePdf = InvoiceGenerator.generateInvoice(orders);
        if(invoicePdf == null){
            return ResponseDto.<byte[]>builder()
                    .message("Failed to generate")
                    .data(null)
                    .build();
        }
        return ResponseDto.<byte[]>builder()
                .code("200")
                .message("Invoice generated successfully")
                .data(invoicePdf)
                .build();
    }
}
