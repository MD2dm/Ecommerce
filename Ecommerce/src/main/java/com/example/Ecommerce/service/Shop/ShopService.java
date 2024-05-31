package com.example.Ecommerce.service.Shop;

import com.example.Ecommerce.dto.SellerDto.Request.Shop.CreateShopDTO;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;

import java.nio.file.AccessDeniedException;

public interface ShopService {

    Shop saveShop(CreateShopDTO request, User user) throws AccessDeniedException;
}
