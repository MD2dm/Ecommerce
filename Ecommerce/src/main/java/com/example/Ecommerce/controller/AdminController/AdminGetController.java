package com.example.Ecommerce.controller.AdminController;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.AdminDto.ResponseInfoAllUsersDTO;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.service.Admin.AdminService;
import com.example.Ecommerce.service.Category.CategoryService;
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
@Tag(name = "Admin Get Controller")
public class AdminGetController {

    @Autowired
    private final AdminService adminService;

    @Autowired
    private final CategoryService categoryService;

    @Operation(summary = "Get all category")
    @GetMapping("/category/all")
    public ResponseEntity<?> getAllCategory(){
        try{
            List<Category> categories = categoryService.getAll();

            return ResponseEntity.ok(new ResponseData<>(200,"Get all category successfully", categories));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseError(404, "No category found"));
        }
    }

    @Operation(summary = "Get Category by Id")
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        try {
            Category category = categoryService.getById(id);
            if (category != null) {
                return ResponseEntity.ok(new ResponseData<>(200, "Category information has been found", category));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseError(404, "The requested information was not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500, "Internal Server Error"));
        }
    }


    @Operation(summary = "Get Full Info User in Server")
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUser(){
        try {
            List<ResponseInfoAllUsersDTO> users = adminService.getAll();

            return ResponseEntity.ok(new ResponseData<>(201, "Retrieve all information successfully", users));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseError(404, "The requested information was not found"));
        }
    }

    @Operation(summary = "Find Users By Id")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
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
}
