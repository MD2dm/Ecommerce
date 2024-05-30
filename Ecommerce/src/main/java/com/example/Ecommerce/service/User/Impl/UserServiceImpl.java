package com.example.Ecommerce.service.User.Impl;



import com.example.Ecommerce.common.File.S3Service;
import com.example.Ecommerce.common.exceptionHandling.DuplicateEntryException;
import com.example.Ecommerce.dto.UsersDto.UserUpdateRequestDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<User> updateUser(Long userId, UserUpdateRequestDTO request, MultipartFile avatar) throws IOException {
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
                if (avatar != null && !avatar.isEmpty()) {
                    String avatarUrl = s3Service.uploadFile(avatar);
                    user.setAvatar(avatarUrl);
                }


                user.setBirthday(request.getBirthday());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setAddress(request.getAddress());

                //check phone
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
}
