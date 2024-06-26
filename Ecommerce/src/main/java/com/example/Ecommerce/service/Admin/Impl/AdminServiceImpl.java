package com.example.Ecommerce.service.Admin.Impl;

import com.example.Ecommerce.dto.AdminDto.AdminMapper;
import com.example.Ecommerce.dto.AdminDto.ResponseInfoAllUsersDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ResponseInfoAllUsersDTO> getAll(){
        List<User> users = userRepository.findAll();
        List<ResponseInfoAllUsersDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            ResponseInfoAllUsersDTO userDTO = AdminMapper.infoAllUsersDTO(user);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Override
    public ResponseInfoAllUsersDTO getById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return AdminMapper.infoAllUsersDTO(user);
        } else {
            return null;
        }
    }

    @Override
    public ResponseInfoAllUsersDTO getByEmail(String email) {
//        User user = userRepository.findByEmail(email);
//        if (user != null) {
//            return convertToDTO(user);
//        } else {
            return null;
//        }
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                userRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
