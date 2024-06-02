package com.example.Ecommerce.controller.SellerController.Products;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.SellerDto.Request.Product.CreateProductDTO;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.service.Product.ProductService;
import com.example.Ecommerce.service.Shop.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
@Tag(name = "Seller Product Controller")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final ShopService shopService;

    @PostMapping("/product/add")
    public ResponseEntity<?> saveProduct(@AuthenticationPrincipal User user,
                                         @RequestPart("createProductDTO") CreateProductDTO createProductDTO,
                                         @RequestPart("filesUrls") List<MultipartFile> filesUrls){
        createProductDTO.setFileUrls(filesUrls);

        Product createdProduct = productService.saveProduct(user, createProductDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201,"Create product successfully", createdProduct));
    }
}
