package com.project.group9.craftycarnival.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class BucketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private CarnivalProducts carnivalProducts;
    private int cartItemQuantity;
    private String cartItemUserEmail;
    @Enumerated(EnumType.STRING)
    private BucketItemType bucketItemType;
}

