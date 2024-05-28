package com.example.Ecommerce.service.Category.CategoryImpl;

import com.example.Ecommerce.dto.CategoryDto.RequestCategoryDTO;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.service.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            return category;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found");
        }
    }

    @Override
    public Category saveCategory(RequestCategoryDTO request) {

        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Category name already exists");
        }

        Category category = new Category();

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());


        return categoryRepository.save(category);
    }

    @Override
    public boolean deleteCategory(Long id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                categoryRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Category updateCategory(Long id, RequestCategoryDTO request) {
        Optional<Category> updateCategory = categoryRepository.findById(id);

        if (updateCategory.isPresent()){
            Category category = updateCategory.get();

            if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
                throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Category name already exists");
            } else {
                category.setCategoryName(request.getCategoryName());
            }

            category.setDescription(request.getDescription());

            return categoryRepository.save(category);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No category found");
        }
    }
}
