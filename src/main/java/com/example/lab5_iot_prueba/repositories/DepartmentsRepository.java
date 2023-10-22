package com.example.lab5_iot_prueba.repositories;


import com.example.lab5_iot_prueba.entitys.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepository extends JpaRepository<Departments, Integer> {
}
