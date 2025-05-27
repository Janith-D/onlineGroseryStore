package com.example.online_grocery_delivery.Repo;

import com.example.online_grocery_delivery.Dto.ProductDto;
import com.example.online_grocery_delivery.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product ,Long>{

    Product findById(long id);
}
