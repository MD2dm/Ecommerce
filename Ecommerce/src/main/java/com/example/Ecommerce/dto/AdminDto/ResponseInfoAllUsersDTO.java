package com.example.Ecommerce.dto.AdminDto;

import com.example.Ecommerce.common.enums.Gender;
import com.example.Ecommerce.common.enums.Role;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class ResponseInfoAllUsersDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private Gender gender;

    @JsonSerialize(using = ToStringSerializer.class)
    private Date birthday;

    private String firstName;

    private String lastName;

    private String phone;

    private String address;

    private Role role;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateAt;
}
