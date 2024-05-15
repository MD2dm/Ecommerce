package com.example.Ecommerce.service.User;

import com.example.Ecommerce.dto.Users.UserUpdateRequest;
import com.example.Ecommerce.model.User;

public interface UserService {

    User updateUser(Long userId, UserUpdateRequest updateUserRequest);
}
