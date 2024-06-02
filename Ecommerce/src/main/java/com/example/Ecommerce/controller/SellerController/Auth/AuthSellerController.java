package com.example.Ecommerce.controller.SellerController.Auth;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.SellerDto.Mapper.SellerMapper;
import com.example.Ecommerce.dto.SellerDto.Response.InfoSeller.InfoSellerDTO;
import com.example.Ecommerce.dto.UserDto.UpdateUserDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Auth.JWT.AuthenticationService;
import com.example.Ecommerce.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
@Tag(name = "Seller Auth Controller")
public class AuthSellerController {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get info Seller")
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        try {
            String username = principal.getName();
            User user = authenticationService.getUserByUsername(username);

            Long userId = user.getId();
            User userDetails = userService.findUserById(userId);

            InfoSellerDTO infoSellerDTO = SellerMapper.infoSellerDTO(userDetails);

            return ResponseEntity.ok(new ResponseData<>(200, "Retrieve seller information successfully", infoSellerDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseError(404, "Seller information does not exist"));
        }
    }

    @Operation(summary = "Update Info Seller")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user,
                                        @RequestPart("sellerInfo") UpdateUserDTO sellerInfo,
                                        @RequestPart("avatar") MultipartFile avatar) {
        try {
            sellerInfo.setAvatar(avatar);
            User updatedCustomer = userService.updateUser(user.getId(), sellerInfo).getBody();

            return ResponseEntity.ok(new ResponseData<>(200, "Seller updated successfully", updatedCustomer));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseError(400,"Failed to process the avatar file."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500,"An unexpected error occurred"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
