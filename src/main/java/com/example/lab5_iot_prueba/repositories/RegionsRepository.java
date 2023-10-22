package com.example.lab5_iot_prueba.repositories;


import com.example.lab5_iot_prueba.entitys.Regions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionsRepository extends JpaRepository<Regions, Integer> {
}
