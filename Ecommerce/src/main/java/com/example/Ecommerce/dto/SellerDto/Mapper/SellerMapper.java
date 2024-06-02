package com.example.Ecommerce.dto.SellerDto.Mapper;

import com.example.Ecommerce.dto.AdminDto.ResponseInfoAllUsersDTO;
import com.example.Ecommerce.dto.SellerDto.Response.InfoSeller.InfoSellerDTO;
import com.example.Ecommerce.dto.SellerDto.Response.Product.ProductWithShopDTO;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.User;

public class SellerMapper {

    public static InfoSellerDTO infoSellerDTO(User user){
        return InfoSellerDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .gender(user.getGender())
                .avatar(user.getAvatar())
                .birthday(user.getBirthday())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .shop(user.getShop())
                .build();
    }

    public static ProductWithShopDTO infoProduct(Product product, User user){
        return ProductWithShopDTO.builder()
                .id(product.getId())
                .shopName(product.getShop().getShopName())
                .productName(product.getProductName())
                .fileUrls(product.getFileUrls())
                .price(product.getPrice())
                .colors(product.getColors())
                .sizes(product.getSizes())
                .stock(product.getStock())
                .category(product.getCategory().getCategoryName())
                .description(product.getDescription())
                .build();

    }

}
