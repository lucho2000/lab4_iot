package com.example.lab5_iot_prueba.entitys;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name = "regions")
public class Regions {

    @Id
    @Column(name = "region_id")
    private Integer regionId;


    @Column(name = "region_name")
    private String regionName;
}
