package com.example.Ecommerce.model;

import com.example.Ecommerce.common.abstractClasses.AbstractEntity;
import com.example.Ecommerce.common.enums.Gender;
import com.example.Ecommerce.common.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails, Serializable {

    @Column(name = "username", length = 50, unique = true ,nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "email", length = 50, unique = true ,nullable = false)
    private String email;

    @Column(name = "gender", length = 20)
    private Gender gender;

    @Column(name = "avatar", columnDefinition = "varchar(max)")
    @Lob
    private String avatar;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "first_name", length = 50,nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 50,nullable = false)
    private String lastName;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "phone", length = 50)
    private String phone;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<PaymentMethod> paymentMethods;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Shop shop;

    private boolean isVerified;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
