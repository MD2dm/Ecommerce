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

    @Transactional
    public Shop saveShop(Long id,CreateShopDTO request) throws Exception {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole() == Role.SELLER) {
                if (user.getShop() != null) {
                    throw new Exception("User already has a shop");
                }
                Shop shop = new Shop();
                shop.setShopName(request.getShopName());
                shop.setShopAddress(request.getShopAddress());
                shop.setShopPhone(request.getShopPhone());
                shop.setUser(user);
                user.setShop(shop);
                userRepository.save(user);  // Save both user and shop due to cascading
                return shop;
            } else {
                throw new Exception("User is not a seller");
            }
        } else {
            throw new Exception("User not found");
        }
    }
}
