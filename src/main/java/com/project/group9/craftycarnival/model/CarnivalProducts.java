package com.project.group9.craftycarnival.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "product")
public class CarnivalProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String productName;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal productPrice;

    @Column(name = "quantity_available")
    private int productQuantityAvailable;

    @Column(name = "manufacturer")
    private String productManufacturer;
    @Column(name = "category")
    private String productCategory;

    @Column(name = "model")
    private String productModel;

    @Column(name = "image_urls")
    private String[] imageUrls;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + productName + '\'' +
                ", description='" + productDescription + '\'' +
                ", category='" + productCategory + '\'' +
                ", price=" + productPrice +
                ", model='" + productModel + '\'' +
                ", manufacturer='" + productManufacturer + '\'' +
                ", quantityAvailable=" + productQuantityAvailable +
                ", imageUrls=" + imageUrls +
                '}';
    }

}
