package com.example.Ecommerce.controller.AdminController;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.CategoryDto.RequestCategoryDTO;
import com.example.Ecommerce.dto.AuthDto.JwtAuthResponseDTO;
import com.example.Ecommerce.dto.AuthDto.LoginRequestDTO;
import com.example.Ecommerce.model.Category;
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
    private final CategoryService categoryService;

    @Operation(summary = "API add category")
    @PostMapping("/category/add")
    public ResponseEntity<Object> addCategory(@RequestBody RequestCategoryDTO request){
        try{
            Category category = categoryService.saveCategory(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201, "Create category successfully", category));
        } catch (ResponseStatusException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseError(409, "The category already exists"));
        }
    }
}
