package com.example.Ecommerce.dto.SellerDto.Mapper;

import com.example.Ecommerce.dto.SellerDto.Response.InfoSeller.InfoSellerDTO;
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
}
