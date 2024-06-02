package com.example.Ecommerce.controller.SellerController.Products;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.SellerDto.Request.Product.ProductDTO;
import com.example.Ecommerce.dto.SellerDto.Response.Product.ProductWithShopDTO;
import com.example.Ecommerce.model.Product;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.service.Product.ProductService;
import com.example.Ecommerce.service.Shop.ShopService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get Full Product in Shop")
    @GetMapping("/products/all")
    public ResponseEntity<ResponseData<List<ProductWithShopDTO>>> getAllProducts(@AuthenticationPrincipal User user) {
        try {
            List<ProductWithShopDTO> productDTOs = productService.getAllProduct(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseData<>(200, "Get products successfully", productDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseData<>(404, "Products not found", null));
        }
    }

    @PostMapping("/products/add")
    public ResponseEntity<?> saveProduct(@AuthenticationPrincipal User user,
                                         @RequestPart("createProductDTO") ProductDTO createProductDTO,
                                         @RequestPart("filesUrls") List<MultipartFile> filesUrls){
        try {
            createProductDTO.setFileUrls(filesUrls);

            Product createdProduct = productService.saveProduct(user, createProductDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201, "Create product successfully", createdProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseError(400, "Failed to create product"));
        }
    }

    @PatchMapping("/products/update/{id}")
    public ResponseEntity<?> updateProduct(@AuthenticationPrincipal User user,
                                           @PathVariable Long id,
                                           @RequestPart("createProductDTO") ProductDTO createProductDTO,
                                           @RequestPart("filesUrls") List<MultipartFile> filesUrls){
        try {
            createProductDTO.setFileUrls(filesUrls);

            Product createdProduct = productService.updateProduct(id,user, createProductDTO).getBody();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201, "Create product successfully", createdProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseError(400, "Failed to create product"));
        }
    }

    @Operation(summary = "Delete Product by Id")
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try {
            boolean isDeleted = productService.deleteProduct(id);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ResponseData<>(204, "Delete product successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseData<>(404,"The requested product was not found"));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500, "Internal Server Error"));
        }
    }
}
