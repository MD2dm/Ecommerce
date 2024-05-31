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
@Table(name = "product")
public class Product extends AbstractEntity {

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "color",nullable = false)
    private String color;

    @Column(name = "size",nullable = false)
    private String size;

    @Column(name = "stock",nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private Set<Review> reviews;
}
