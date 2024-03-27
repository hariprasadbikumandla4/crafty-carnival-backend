package com.project.group9.craftycarnival.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_addresses")
public class UserAddresses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String name;
    @Column(columnDefinition = "text")
    private String address1;
    @Column(columnDefinition = "text")
    private String address2;
    private String city;
    private String state;
    private long zip;
    private String phone;
}
