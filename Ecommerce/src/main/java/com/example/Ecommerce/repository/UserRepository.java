package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Role;
import com.example.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByRole(Role role);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
