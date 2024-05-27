package com.example.Ecommerce.service.Admin;

import com.example.Ecommerce.dto.AdminDto.ResponseInfoAllUsersDTO;
import com.example.Ecommerce.model.User;

import java.util.List;

public interface AdminService {

    List<ResponseInfoAllUsersDTO> getAll();

    ResponseInfoAllUsersDTO getById(Long id);

    ResponseInfoAllUsersDTO getByEmail(String email);

    boolean deleteUser(Long id);
}
