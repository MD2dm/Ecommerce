package com.example.Ecommerce.controller.SellerController.Shop;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.AdminDto.ResponseInfoAllUsersDTO;
import com.example.Ecommerce.dto.SellerDto.Request.Shop.CreateShopDTO;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.service.Shop.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
@Tag(name = "Seller Shop Controller")
public class ShopController {

    @Autowired
    private final ShopService shopService;

    @Operation(summary = "API add Shop by Seller")
    @PostMapping("/shop/add")
    public ResponseEntity<?> createShop(@RequestBody CreateShopDTO createShopDTO,
                                                     Authentication authentication) {
        try{
            User user = (User) authentication.getPrincipal();
            Long loggedInUserId = user.getId();
            Shop shop = shopService.saveShop(loggedInUserId, createShopDTO);
            return ResponseEntity.ok(new ResponseData<>(201,"Create shop successfully", shop));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseError(400, "Create shop fail"));
        }
    }

    @Operation(summary = "Get info Seller Shop")
    @GetMapping("/shop/info")
    public ResponseEntity<?> getMyShop(@AuthenticationPrincipal User user) {
        try {
            Shop shop = shopService.getShopByUserId(user.getId());
            return ResponseEntity.ok(new ResponseData<>(201,"Get info shop successfully", shop));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseError(404, "You don't have any stores yet"));
        }

    }

}
