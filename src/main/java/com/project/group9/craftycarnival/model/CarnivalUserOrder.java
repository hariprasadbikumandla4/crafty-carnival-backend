package com.project.group9.craftycarnival.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class CarnivalUserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_email")
    private String orderUserEmail;
    @Column(name = "ordered_on")
    private LocalDateTime orderedOn;
    @Column(columnDefinition = "text")
    private String orderAddress;
    @Column(name = "payment_id")
    private String orderPaymentId;

    private Long[] items;

    @Enumerated(EnumType.STRING)
    private CarnivalUserOrderStatus status;

    @Column(name = "order_total_price")
    private BigDecimal orderTotalPrice;

    @Column(name = "delivery_partner")
    private String orderDeliveryPartner;

    @Column(name = "delivery_tracking_number")
    private String deliveryTrackingNumber;
}
