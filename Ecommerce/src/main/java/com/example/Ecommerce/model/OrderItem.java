package com.example.Ecommerce.model;

import com.example.Ecommerce.common.abstractClasses.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_item")
public class OrderItem extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Column(name = "price",nullable = false)
    private Double price;
}
