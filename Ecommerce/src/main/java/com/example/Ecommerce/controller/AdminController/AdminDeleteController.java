package com.example.Ecommerce.controller.AdminController;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.service.Admin.AdminService;
import com.example.Ecommerce.service.Category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Delete Controller")
public class AdminDeleteController {

    @Autowired
    private final AdminService adminService;

    @Autowired
    private final CategoryService categoryService;

    @Operation(summary = "Delete User By Id")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        try {
            boolean isDeleted = adminService.deleteUser(id);
            if (isDeleted) {
                return ResponseEntity.ok(new ResponseData<>(204, "Delete users successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseError(404, "The requested user was not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500, "Internal Server Error"));
        }
    }

    @Operation(summary = "Delete Category by Id")
    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id){
        try {
            boolean isDeleted = categoryService.deleteCategory(id);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ResponseData<>(204, "Delete category successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseData<>(404,"The requested category was not found"));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500, "Internal Server Error"));
        }
    }

}
