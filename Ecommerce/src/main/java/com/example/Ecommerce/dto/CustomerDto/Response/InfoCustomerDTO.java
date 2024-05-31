package com.example.Ecommerce.dto.CustomerDto.Response;

import com.example.Ecommerce.common.enums.Gender;
import com.example.Ecommerce.common.enums.Role;
import com.example.Ecommerce.model.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Builder
@Getter
public class InfoCustomerDTO implements Serializable {

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

    private Set<PaymentMethod> paymentMethods;


}
