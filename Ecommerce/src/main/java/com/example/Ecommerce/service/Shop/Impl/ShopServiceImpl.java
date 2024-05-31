package com.example.Ecommerce.service.Shop.Impl;

import com.example.Ecommerce.common.enums.Role;
import com.example.Ecommerce.dto.SellerDto.Request.Shop.CreateShopDTO;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.ShopRepository;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Shop.ShopService;
import com.example.Ecommerce.service.User.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Shop saveShop(CreateShopDTO request,User user) throws AccessDeniedException {
        if (user.getRole() == Role.SELLER) {
            Shop shop = new Shop();

            shop.setShopName(request.getShopName());
            shop.setShopAddress(request.getShopAddress());
            shop.setShopPhone(request.getShopPhone());

            Shop savedShop = shopRepository.save(shop);
            user.setShop(savedShop);
            userRepository.save(user);

            return savedShop;
        } else {
            throw new AccessDeniedException("User is not a seller");
        }
    }
}
