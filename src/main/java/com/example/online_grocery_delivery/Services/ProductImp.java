package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.ProductDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;
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
    public ResponseDto<ProductDto> createProduct(ProductDto productDto) {
        ResponseDto<ProductDto> responseDto = new ResponseDto<>();

        Product exitingProduct = productRepo.findById(productDto.getId());
        if(exitingProduct != null){
            responseDto.setCode("ERROR");
            responseDto.setMessage("The Product All Ready ");
            return responseDto;
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        productRepo.save(product);
        responseDto.setCode("SUCCESS");
        responseDto.setMessage("Product created successfully");
        return responseDto;
    }

    @Override
    public ResponseDto<ProductDto> getAllProducts(){
        ResponseDto<ProductDto> responseDto = new ResponseDto<>();

        List<Product> products = productRepo.findAll();
        if (products.isEmpty()){
            responseDto.setCode("ERROR");
            responseDto.setMessage("No Product is Found");
            return responseDto;
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

        responseDto.setCode("SUCCESS");
        responseDto.setMessage("Products retrieved successfully");
        responseDto.setDataList(productDto);
        return responseDto;
    }
}
