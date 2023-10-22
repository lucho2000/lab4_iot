package com.example.lab5_iot_prueba.repositories;


import com.example.lab5_iot_prueba.entitys.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsRepository extends JpaRepository<Locations, Integer> {

}
