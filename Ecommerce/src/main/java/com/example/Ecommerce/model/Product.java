package com.example.Ecommerce.model;

import com.example.Ecommerce.common.abstractClasses.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product extends AbstractEntity {

    @ElementCollection
    @CollectionTable(name = "product_files", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "file_url")
    private List<String> fileUrls;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "price",nullable = false)
    private Double price;

    @ElementCollection
    @CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "colors")
    private Set<String> colors = new HashSet<>();;

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "sizes")
    private Set<String> sizes = new HashSet<>();

    @Column(name = "stock",nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonBackReference
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private Set<Review> reviews;
}
