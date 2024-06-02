package com.example.Ecommerce.service.Product;

import com.example.Ecommerce.dto.SellerDto.Request.Product.ProductDTO;
import com.example.Ecommerce.dto.SellerDto.Response.Product.ProductWithShopDTO;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<ProductWithShopDTO> getAllProduct(User user);

    Product saveProduct(User user, ProductDTO createProductDTO);

    boolean deleteProduct(Long id);

    ResponseEntity<Product> updateProduct(Long id, User user, ProductDTO productDTO);

}
