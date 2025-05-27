package com.example.online_grocery_delivery.Repo;

import com.example.online_grocery_delivery.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {
}
