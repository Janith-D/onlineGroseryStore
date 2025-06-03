package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.EmailDto;
import com.example.online_grocery_delivery.Dto.OrderDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;
import com.example.online_grocery_delivery.Entity.Cart;
import com.example.online_grocery_delivery.Entity.Orders;
import com.example.online_grocery_delivery.Repo.CartRepo;
import com.example.online_grocery_delivery.Repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderImp implements OrderService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private EmailImp emailImp;

    @Override
    public ResponseDto<?> placeOrder() {
        ResponseDto<OrderDto> responce = new ResponseDto<>();

        List<Cart> cartItems = cartRepo.findAll();
        if(cartItems.isEmpty()){
            responce.setCode("ERROR");
            responce.setMessage("Cart is empty");
            return responce;
        }
        double total = cartItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
        Orders orders = new Orders();
        orders.setItems(cartItems);
        orders.setTime(LocalDateTime.now());
        orders.setTotalPrice(total);
        orders.setCustomerName(orders.getCustomerName());
        orderRepo.save(orders);
        cartRepo.deleteAll();

        List<CartDto> cartItemsDto = cartItems.stream().map(item ->
                new CartDto(item.getProduct().getId(),item.getQuantity())
        ).collect(Collectors.toList());

        OrderDto orderDto = OrderDto.builder()
                .id(orders.getId())
                .time(orders.getTime())
                .totalPrice(orders.getTotalPrice())
                .items(cartItemsDto)
                .build();

        EmailDto emailDto = new EmailDto();
        emailDto.setRecipient(orderDto.getCustomerName());
        emailDto.setSubject("ORDER PLACEMENT");
        emailDto.setMessageBody("Dear Customer your order is Placement!");
        emailImp.sendEmail(emailDto);

        responce.setCode("SUCCESS");
        responce.setMessage("Order placed successfully");
        responce.setData(orderDto);
        return responce;
    }

    @Override
    public ResponseDto<OrderDto> getOrderById(Long oderId) {
        ResponseDto<OrderDto> response = new ResponseDto<>();

        Optional<Orders> optionalOrders = orderRepo.findById(oderId);
        if(optionalOrders.isEmpty()){
            response.setCode("ERROR");
            response.setMessage("Order not found with Id : "+ oderId);
            return response;
        }
        Orders orders = optionalOrders.get();

        OrderDto dto = new OrderDto();
        dto.setId(orders.getId());
        dto.setTime(orders.getTime());
        dto.setTotalPrice(orders.getTotalPrice());
        dto.setCustomerName(orders.getCustomerName());

        response.setCode("SUCCESS");
        response.setMessage("Order retrieved successfully");
        response.setData(dto);
        return response;
    }
}
