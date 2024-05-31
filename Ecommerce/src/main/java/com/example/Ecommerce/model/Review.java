package com.example.Ecommerce.model;

import com.example.Ecommerce.common.abstractClasses.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "review")
@EntityListeners(AuditingEntityListener.class)
public class Review extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column(name = "rating",nullable = false)
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date reviewDate;

    @LastModifiedDate
    @Column(nullable = false)
    private Date lastModifiedDate;
}
