package com.example.Ecommerce.dto.SellerDto.Response.InfoSeller;


import com.example.Ecommerce.common.enums.Gender;
import com.example.Ecommerce.common.enums.Role;
import com.example.Ecommerce.model.Shop;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class InfoSellerDTO implements Serializable {

    private Long id;

    private String username;

    private Role role;

    private String email;

    private Gender gender;

    private String avatar;

    private Date birthday;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private Shop shop;
}
