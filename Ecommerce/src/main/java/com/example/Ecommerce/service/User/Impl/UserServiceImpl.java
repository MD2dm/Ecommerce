package com.example.Ecommerce.service.User.Impl;

import com.example.Ecommerce.common.File.S3Service;
import com.example.Ecommerce.dto.UserDto.UpdateUserDTO;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final S3Service s3Service;

    @Override
    public ResponseEntity<User> updateUser(Long userId, UpdateUserDTO request ) throws IOException {
        {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                //check email
                if (userRepository.existsByEmail(request.getEmail())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
                } else {
                    user.setEmail(request.getEmail());
                }

                //check avatar
                String avatarUrl = s3Service.uploadSingleFile(request.getAvatar());
                user.setAvatar(avatarUrl);

                user.setBirthday(request.getBirthday());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setAddress(request.getAddress());

                if (userRepository.existsByPhone(request.getPhone())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone already exists");
                } else {
                    user.setPhone(request.getPhone());
                }



                User savedUser = userRepository.save(user);

                return ResponseEntity.ok(savedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @Override
    public Shop getShopByUserId(Long userId) {
        User user = findUserById(userId);
        return user.getShop();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
