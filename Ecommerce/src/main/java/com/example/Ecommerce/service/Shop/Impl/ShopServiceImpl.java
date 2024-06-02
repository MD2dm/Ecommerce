package com.example.Ecommerce.service.Shop.Impl;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.example.Ecommerce.common.enums.Role;
import com.example.Ecommerce.dto.SellerDto.Request.Shop.CreateShopDTO;
import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.ProductRepository;
import com.example.Ecommerce.repository.ShopRepository;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Auth.JWT.JWTService;
import com.example.Ecommerce.service.Shop.ShopService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JWTService jwtService;

    @Override
    @Transactional
    public Shop saveShop(Long id,CreateShopDTO createShopDTO) throws Exception {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole() == Role.SELLER) {
                if (user.getShop() != null) {
                    throw new Exception("User already has a shop");
                }
                Shop shop = new Shop();
                shop.setShopName(createShopDTO.getShopName());
                shop.setShopAddress(createShopDTO.getShopAddress());
                shop.setShopPhone(createShopDTO.getShopPhone());
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

    @Override
    public Shop getShopByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return shopRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found"));
    }
}
