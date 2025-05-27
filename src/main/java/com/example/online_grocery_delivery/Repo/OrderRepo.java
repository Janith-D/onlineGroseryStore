package com.example.online_grocery_delivery.Repo;

import com.example.online_grocery_delivery.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
