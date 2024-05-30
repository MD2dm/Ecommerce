package com.example.Ecommerce.dto.SellerDto.Mapper;

import com.example.Ecommerce.dto.SellerDto.Response.ResponseInfoSeller.RequestInfoSellerDTO;
import com.example.Ecommerce.model.User;

public class SellerInfoMapper {
    public static RequestInfoSellerDTO infoSellerDTO(User user){
        return RequestInfoSellerDTO.builder()
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
