package com.example.Ecommerce.controller.CustomerController.Auth;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.CustomerDto.Mapper.CustomerMapper;
import com.example.Ecommerce.dto.CustomerDto.Request.UpdateCustomerDTO;
import com.example.Ecommerce.dto.CustomerDto.Response.InfoCustomerDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.service.Auth.JWT.AuthenticationService;
import com.example.Ecommerce.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Auth Controller")
public class AuthCustomerController {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Update Info User")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                             @RequestPart("userInfo") UpdateCustomerDTO userInfo,
                                             @RequestPart("avatar") MultipartFile avatar) {
        try {
            userInfo.setAvatar(avatar);
            userService.updateUser(id, userInfo);
            return ResponseEntity.ok("User updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseError(400,"Failed to process the avatar file."));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseError(404,"Failed to process the avatar file."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500,"An unexpected error occurred"));
        }
    }


    @Operation(summary = "Get info customer")
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        try {
            User user = authenticationService.getUserByUsername(principal.getName());
            InfoCustomerDTO infoCustomerDTO = CustomerMapper.infoCustomerDTO(user);

            return ResponseEntity.ok(new ResponseData<>(200, "Retrieve customer information successfully", infoCustomerDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseData<>(404, "Customer information does not exist", null));
        }
    }
}
