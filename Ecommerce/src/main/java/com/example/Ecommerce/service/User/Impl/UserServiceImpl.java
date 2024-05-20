package com.example.Ecommerce.service.User.Impl;


import com.example.Ecommerce.dto.Users.UserUpdateRequest;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.File.FileService;
import com.example.Ecommerce.service.User.UserService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final FileService fileService;


    @Override
    public ResponseEntity<User> updateUser(Long userId, UserUpdateRequest updateUserRequest, MultipartFile avatar) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(updateUserRequest.getEmail());

//            try {
//                if (avatar != null && !avatar.isEmpty()) {
//                    String avatarUrl = fileService.upload(avatar);
//                    user.setAvatar(avatarUrl);
//                }
//            } catch (IOException e) {
//                // Xử lý lỗi upload file
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            } catch (java.io.IOException e) {
//                throw new RuntimeException(e);
//            }

            user.setBirthday(updateUserRequest.getBirthday());
            user.setFirstName(updateUserRequest.getFirstName());
            user.setLastName(updateUserRequest.getLastName());
            user.setAddress(updateUserRequest.getAddress());
            user.setPhone(updateUserRequest.getPhone());

            User savedUser = userRepository.save(user);

            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
