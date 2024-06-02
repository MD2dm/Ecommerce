package com.example.Ecommerce.dto.SellerDto.Request.Product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductDTO implements Serializable {

    private String productName;

    private List<MultipartFile> fileUrls = new ArrayList<>();;

    private String description;

    private Double price;

    private Set<String> colors;

    private Set<String> sizes;

    private Integer stock;

    private String category;

}
