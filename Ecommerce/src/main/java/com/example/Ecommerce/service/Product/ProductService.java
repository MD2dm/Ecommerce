package com.example.Ecommerce.service.Product;

import com.example.Ecommerce.dto.SellerDto.Request.Product.CreateProductDTO;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.User;

public interface ProductService {

    Product saveProduct(User user, CreateProductDTO createProductDTO);
}
