package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.ProductDto;
import com.example.online_grocery_delivery.Dto.ResponceDto;
import com.example.online_grocery_delivery.Entity.Product;
import com.example.online_grocery_delivery.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImp implements ProductService{

    @Autowired
    private ProductRepo productRepo;

    @Override
    public ResponceDto createProduct(ProductDto productDto) {
        ResponceDto responceDto = new ResponceDto();

        Product exitingProduct = productRepo.findById(productDto.getId());
        if(exitingProduct != null){
            responceDto.setCode("ERROR");
            responceDto.setMessage("The Product All Ready ");
            return responceDto;
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        productRepo.save(product);
        responceDto.setCode("SUCCESS");
        responceDto.setMessage("Product created successfully");
        return responceDto;
    }

    @Override
    public ResponceDto getAllProducts(){
        ResponceDto responceDto = new ResponceDto();

        List<Product> products = productRepo.findAll();
        if (products.isEmpty()){
            responceDto.setCode("ERROR");
            responceDto.setMessage("No Product is Found");
            return responceDto;
        }
        List<ProductDto> productDto = products.stream().map(product -> {
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());
            return dto;
        }).collect(Collectors.toList());

        responceDto.setCode("SUCCESS");
        responceDto.setMessage("Products retrieved successfully");
        responceDto.setData(productDto);
        return responceDto;
    }
}
