package com.example.Ecommerce.controller.SellerController.Shop;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.dto.CategoryDto.RequestCategoryDTO;
import com.example.Ecommerce.dto.SellerDto.Request.Shop.CreateShopDTO;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Shop.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Seller Shop Controller")
public class ShopController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ShopService shopService;

    @Operation(summary = "API add Shop by Seller")
    @PostMapping("/sellers/shop/add")
    public ResponseEntity<Object> addCategory(@RequestBody CreateShopDTO request,  Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        try{
            Shop shop = shopService.saveShop(request, user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseData<>(201, "Create hop successfully", shop));
        } catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ResponseData<>(e.getStatusCode().value(), e.getReason()));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}
