package com.project.group9.craftycarnival.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "manufacturer")
public class CarnivalManufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String manufacturerName;

    private String manufacturerPhone;

    private String manufacturerEmail;

    private String manufacturerWebsite;

    private String manufacturerAddress;

}
