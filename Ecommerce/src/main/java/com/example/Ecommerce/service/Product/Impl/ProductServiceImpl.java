package com.example.Ecommerce.service.Product.Impl;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.example.Ecommerce.common.File.S3Service;
import com.example.Ecommerce.dto.SellerDto.Request.Product.CreateProductDTO;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.repository.ProductRepository;

import com.example.Ecommerce.service.Product.ProductService;
import com.example.Ecommerce.service.Shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private S3Service s3Service;

    @Override
    public Product saveProduct(User user, CreateProductDTO createProductDTO){
        Shop shop = user.getShop();

        if (shop == null) {
            throw new RuntimeException("User does not have a shop");
        }

        Product product = new Product();

        product.setProductName(createProductDTO.getProductName());
        product.setDescription(createProductDTO.getDescription());
        product.setPrice(createProductDTO.getPrice());

        Category category = categoryRepository.findByCategoryName(createProductDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
        product.setCategory(category);

        product.setStock(createProductDTO.getStock());

        List<String> files = s3Service.uploadFiles(createProductDTO.getFileUrls());

        product.setFileUrls(files);

        Set<String> colors = createProductDTO.getColors() != null ? createProductDTO.getColors() : new HashSet<>();
        Set<String> sizes = createProductDTO.getSizes() != null ? createProductDTO.getSizes() : new HashSet<>();

        product.setColors(colors);
        product.setSizes(sizes);

        product.setShop(shop);



        return productRepository.save(product);

    }

}
