package com.example.lab5_iot_prueba.repositories;


import com.example.lab5_iot_prueba.entitys.Employees;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM hr_v2.employees where manager_id = ?1")
    List<Employees> obtenerListaTrabajadoresPorTutorId(String tutor_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE employees e SET e.meeting=1, e.meeting_date=?1 where e.employee_id = ?2")
    void updateMeeting(String meeting_date, String employee_id );

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE employees e SET e.employee_feedback=?1 where e.employee_id = ?2")
    void updateMeetingFeedback(String meeting_feedback, String employee_id );
}
