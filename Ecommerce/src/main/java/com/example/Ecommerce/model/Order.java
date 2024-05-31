package com.example.Ecommerce.model;

import com.example.Ecommerce.common.abstractClasses.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @CreatedDate
    @Column(name = "order_date",nullable = false)
    private Date orderDate;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;

}
