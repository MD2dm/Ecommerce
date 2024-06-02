package com.example.Ecommerce.controller.AdminController;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.dto.CategoryDto.RequestCategoryDTO;
import com.example.Ecommerce.model.Category;
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
@Tag(name = "Admin Update Controller")
public class AdminUpdateController {

    @Autowired
    private final CategoryService categoryService;

    @Operation(summary = "Update category by Admin")
    @PutMapping("/category/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id,
                                                 @RequestBody RequestCategoryDTO request){
        try {
            Category updateCategory = categoryService.updateCategory(id, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201,"Update category successful", updateCategory));
        } catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ResponseData<>(e.getStatusCode().value(), e.getReason()));
        }
    }
}
