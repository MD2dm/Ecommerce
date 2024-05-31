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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
@Tag(name = "Seller Shop Controller")
public class ShopController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ShopService shopService;

    @Operation(summary = "API add Shop by Seller")
    @PostMapping("/shop/add")
    public ResponseEntity<?> createShopForLoggedInUser(@RequestBody CreateShopDTO createShopDTO,
                                                       Authentication authentication) {
        try{
            User user = (User) authentication.getPrincipal();
            Long loggedInUserId = user.getId();
            Shop shop = shopService.saveShop(loggedInUserId, createShopDTO);
            return ResponseEntity.ok(shop);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new ResponseData<>(201, "Create hop successfully", shop));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
