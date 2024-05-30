package com.example.Ecommerce.model;

import com.example.Ecommerce.common.abstractClasses.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment_method")
public class PaymentMethod extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_number",nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "expiryDate",nullable = false)
    private Date expiryDate;

    @Column(name = "cvv",nullable = false, length = 3)
    private String cvv;

    @Column(name = "card_holder_name",nullable = false)
    private String cardholderName;
}
