package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Shop;
import com.example.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByShopName(String shopName);

    Optional<Shop> findByUser(User user);

    Optional<Shop> findByUser_Id(Long userId);
}
