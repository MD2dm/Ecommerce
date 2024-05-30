package com.example.Ecommerce.common.abstractClasses;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_sequence")
    @SequenceGenerator(
            name = "custom_sequence",
            sequenceName = "start_from_100000",
            initialValue = 100000,
            allocationSize = 1
    )
    private Long Id;

    @Column(name = "create_at")
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Column(name = "update_ad")
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updateAt;
}
