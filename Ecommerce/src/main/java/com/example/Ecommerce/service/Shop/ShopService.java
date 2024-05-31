package com.example.Ecommerce.service.Shop;


import com.example.Ecommerce.dto.SellerDto.Request.Shop.CreateShopDTO;
import com.example.Ecommerce.model.Shop;

public interface ShopService {

    Shop saveShop(Long id, CreateShopDTO request) throws Exception;

}
