package com.example.Ecommerce.service.User;

import com.example.Ecommerce.dto.Users.UserUpdateRequest;
import com.example.Ecommerce.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    ResponseEntity<User> updateUser(Long userId, UserUpdateRequest updateUserRequest, MultipartFile avatar);
}
