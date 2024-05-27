package com.example.Ecommerce.controller.AdminController;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.AdminDto.ResponseInfoAllUsersDTO;
import com.example.Ecommerce.dto.UsersDto.RegisterRequestDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Admin.AdminService;
import com.example.Ecommerce.service.Auth.JWT.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final UserRepository userRepository;

    @Operation(summary = "Get Full Info User in Server")
    @GetMapping("/info/all-users")
    public ResponseEntity<Object> getAllUser(){
        try {
            List<ResponseInfoAllUsersDTO> users = adminService.getAll();

            return ResponseEntity.ok(new ResponseData<>(201, "Retrieve all information successfully", users));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseError(404, "The requested information was not found"));
        }
    }

    @Operation(summary = "Find Users By Id")
    @GetMapping("/info/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        try {
            ResponseInfoAllUsersDTO userDTO = adminService.getById(id);
            if (userDTO != null) {
                return ResponseEntity.ok(new ResponseData<>(200, "User information has been found", userDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseError(404, "The requested information was not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500, "Internal Server Error"));
        }
    }

    @Operation(summary = "Register Account User By Admin")
    @PostMapping("/register")
    public ResponseEntity<ResponseData<Void>> register(@RequestBody RegisterRequestDTO request) {
        // Check username, email, phone, password
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Username already exists", null));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Email already exists", null));
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Phone already exists", null));
        }

        String password = request.getPassword();
        if (password.length() < 8 || !password.matches(".*[A-Z].*")) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Password must be at least 8 characters long and contain at least one uppercase letter", null));
        }

        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseData<>(201, "OTP sent to email. Please verify.", null));
    }

    @Operation(summary = "Verify OTP and Complete Registration")
    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseData<User>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        try {
            User user = authenticationService.verifyOtp(email, otp);
            return ResponseEntity.ok(new ResponseData<>(201, "User registered successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseData<>(401, "Invalid OTP", null));
        }
    }

    @Operation(summary = "Delete User By Id")
    @DeleteMapping("/info/users/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        try {
            boolean isDeleted = adminService.deleteUser(id);
            if (isDeleted) {
                return ResponseEntity.ok(new ResponseData<>(200, "User has been deleted successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseError(404, "The requested user was not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500, "Internal Server Error"));
        }
    }
}
