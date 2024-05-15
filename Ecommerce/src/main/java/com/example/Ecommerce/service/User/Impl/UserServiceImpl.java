package com.example.Ecommerce.service.User.Impl;


import com.example.Ecommerce.dto.Users.UserUpdateRequest;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.User.UserService;
import com.example.Ecommerce.ultils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User updateUser(Long userId, UserUpdateRequest updateUserRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setEmail(updateUserRequest.getEmail());

            String newAvatarPath = updateUserRequest.getAvatar();
            if (newAvatarPath != null && !newAvatarPath.isEmpty()) {
                user.setAvatar(newAvatarPath);
            } else {
                user.setAvatar(updateUserRequest.getAvatar());
            }

            user.setBirthday(updateUserRequest.getBirthday());
            user.setFirstName(updateUserRequest.getFirstName());
            user.setLastName(updateUserRequest.getLastName());
            user.setAddress(updateUserRequest.getAddress());
            user.setPhone(updateUserRequest.getPhone());


            return userRepository.save(user);

        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

}
