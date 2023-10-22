package com.example.lab5_iot_prueba.repositories;

import com.example.lab5_iot_prueba.entitys.Countries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountriesRepository extends JpaRepository<Countries, String> {
}
