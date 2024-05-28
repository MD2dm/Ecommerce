package com.example.Ecommerce.dto.CategoryDto;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class RequestCategoryDTO implements Serializable {

    private String categoryName;

    private String description;

}
