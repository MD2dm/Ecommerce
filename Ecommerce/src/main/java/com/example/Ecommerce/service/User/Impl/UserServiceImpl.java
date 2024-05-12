package com.example.Ecommerce.service.User.Impl;


import com.example.Ecommerce.dto.Users.UserUpdate;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

}
