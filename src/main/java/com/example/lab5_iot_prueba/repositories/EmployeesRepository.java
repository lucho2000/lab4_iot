package com.example.lab5_iot_prueba.repositories;


import com.example.lab5_iot_prueba.entitys.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM hr_v2.employees where manager_id = ?1")
    List<Employees> obtenerListaTrabajadoresPorTutorId(String tutor_id);
}
