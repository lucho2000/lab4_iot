package com.example.lab5_iot_prueba.entitys;


import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name="locations")
public class Locations {

    @Id
    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Countries countryId;
}
