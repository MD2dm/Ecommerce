package com.example.Ecommerce.service.User.Impl;


import com.example.Ecommerce.dto.UsersDto.UserUpdateRequestDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<User> updateUser(Long userId, UserUpdateRequestDTO request, MultipartFile avatar) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(request.getEmail());

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

            user.setBirthday(request.getBirthday());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setAddress(request.getAddress());
            user.setPhone(request.getPhone());

            User savedUser = userRepository.save(user);

            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
