package com.example.Ecommerce.service.Product.Impl;

import com.example.Ecommerce.common.File.S3Service;
import com.example.Ecommerce.dto.SellerDto.Mapper.SellerMapper;
import com.example.Ecommerce.dto.SellerDto.Request.Product.ProductDTO;
import com.example.Ecommerce.dto.SellerDto.Response.Product.ProductWithShopDTO;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.repository.ProductRepository;

import com.example.Ecommerce.service.Product.ProductService;
import com.example.Ecommerce.service.Shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<ProductWithShopDTO> getAllProduct(User user){

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> SellerMapper.infoProduct(product, user))
                .collect(Collectors.toList());
    }

    @Override
    public Product saveProduct(User user, ProductDTO createProductDTO){
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

    @Override
    public ResponseEntity<Product> updateProduct(Long id, User user, ProductDTO productDTO){

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();

            if (productDTO.getProductName() != null) {
                product.setProductName(productDTO.getProductName());
            }

            if (productDTO.getDescription() != null) {
                product.setDescription(productDTO.getDescription());
            }

            if (productDTO.getPrice() != null) {
                product.setPrice(productDTO.getPrice());
            }

            if (productDTO.getCategory() != null) {
                Category category = categoryRepository.findByCategoryName(productDTO.getCategory())
                        .orElseThrow(() -> new RuntimeException("Category Not Found"));
                product.setCategory(category);
            }

            if (productDTO.getStock() != null) {
                product.setStock(productDTO.getStock());
            }

            if (productDTO.getFileUrls() != null && !productDTO.getFileUrls().isEmpty()) {
                List<String> files = s3Service.uploadFiles(productDTO.getFileUrls());
                product.setFileUrls(files);
            }

            if (productDTO.getColors() != null) {
                Set<String> colors = new HashSet<>(productDTO.getColors());
                product.setColors(colors);
            }

            if (productDTO.getSizes() != null) {
                Set<String> sizes = new HashSet<>(productDTO.getSizes());
                product.setSizes(sizes);
            }

            Product productUpdate = productRepository.save(product);

            return ResponseEntity.ok(productUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public boolean deleteProduct(Long id){
        try {
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()) {
                productRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
}
