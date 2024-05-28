package com.example.Ecommerce.service.Category;

import com.example.Ecommerce.dto.AdminDto.ResponseInfoAllUsersDTO;
import com.example.Ecommerce.dto.CategoryDto.RequestCategoryDTO;
import com.example.Ecommerce.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(Long id);

    Category saveCategory(RequestCategoryDTO request);

    Category updateCategory(Long id, RequestCategoryDTO request);

    boolean deleteCategory(Long id);

}
