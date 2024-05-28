package com.example.Ecommerce.controller.AdminController;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.CategoryDto.RequestCategoryDTO;
import com.example.Ecommerce.dto.UsersDto.RegisterRequestDTO;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Auth.JWT.AuthenticationService;
import com.example.Ecommerce.service.Category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Save Controller")
public class AdminSaveController {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CategoryService categoryService;

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
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201,"User registered successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseData<>(401, "Invalid OTP", null));
        }
    }

    @Operation(summary = "API add category")
    @PostMapping("/category/add")
    public ResponseEntity<Object> addCategory(@RequestBody RequestCategoryDTO request){
        try{
            Category category = categoryService.saveCategory(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201, "Create category successfully", category));
        } catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ResponseData<>(e.getStatusCode().value(), e.getReason()));
        }
    }
}
