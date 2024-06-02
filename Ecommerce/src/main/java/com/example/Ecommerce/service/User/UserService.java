package com.example.Ecommerce.service.User;

import com.example.Ecommerce.dto.UserDto.UpdateUserDTO;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UserService {

    ResponseEntity<User> updateUser(Long userId, UpdateUserDTO request) throws IOException;

    User findUserById(Long id);

    Shop getShopByUserId(Long userId);
}
