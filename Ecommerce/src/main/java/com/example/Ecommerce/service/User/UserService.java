package com.example.Ecommerce.service.User;

import com.example.Ecommerce.dto.UsersDto.UserUpdateRequestDTO;
import com.example.Ecommerce.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    ResponseEntity<User> updateUser(Long userId, UserUpdateRequestDTO request, MultipartFile avatar) throws  IOException;
}
