package com.example.Ecommerce.model;

import com.example.Ecommerce.common.abstractClasses.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shop")
public class Shop extends AbstractEntity {

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "shop_address", nullable = false)
    private String shopAddress;

    @Column(name = "shop_phone", nullable = false)
    private String shopPhone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "shop")
    private Set<Product> products;

}
