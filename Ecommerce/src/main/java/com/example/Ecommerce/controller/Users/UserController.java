package com.example.Ecommerce.controller.Users;

import com.example.Ecommerce.dto.Users.UserUpdateRequest;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.service.User.UserService;
import com.example.Ecommerce.ultils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;
    private final FileUploadUtil fileUploadUtil;

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello Users");
    }

    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUsers(@PathVariable("id") Long id,
                                            @RequestPart("updateUserRequest") UserUpdateRequest userUpdateRequest,
                                            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile) throws IOException {

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarPath = fileUploadUtil.uploadFile(avatarFile).toString();
            userUpdateRequest.setAvatar(avatarPath);
        }

        User updatedUser = userService.updateUser(id, userUpdateRequest);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
