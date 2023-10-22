package com.example.lab5_iot_prueba.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "countries")
public class Countries {

    @Id
    @Column(name = "country_id", nullable = false)
    private String countryId;

    @Column(name = "country_name")
    private String countryName;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Regions region;



}
