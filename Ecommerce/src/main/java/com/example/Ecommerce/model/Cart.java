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
@Table(name = "carts")
public class Cart extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @OneToMany(mappedBy = "cart")
    @Column(name = "cart_items")
    private Set<CartItem> cartItems;
}
