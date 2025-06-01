package com.example.online_grocery_delivery.Repo;

import com.example.online_grocery_delivery.Entity.Orders;
import com.example.online_grocery_delivery.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders,Long> {
}
