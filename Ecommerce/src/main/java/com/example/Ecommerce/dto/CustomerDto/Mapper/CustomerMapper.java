package com.example.Ecommerce.dto.CustomerDto.Mapper;

import com.example.Ecommerce.dto.CustomerDto.Response.InfoCustomerDTO;
import com.example.Ecommerce.model.User;

public class CustomerMapper {
    public static InfoCustomerDTO infoCustomerDTO(User user){
        return InfoCustomerDTO.builder()
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
                .paymentMethods(user.getPaymentMethods())
                .build();
    }
}
