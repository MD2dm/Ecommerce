package com.example.Ecommerce.dto.AdminDto;

import com.example.Ecommerce.model.User;

public class AdminMapper {

    public static ResponseInfoAllUsersDTO infoAllUsersDTO(User user){
        return ResponseInfoAllUsersDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .build();
    }
}
