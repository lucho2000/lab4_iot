package com.example.lab5_iot_prueba.repositories;


import com.example.lab5_iot_prueba.entitys.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Jobs, String> {

}
