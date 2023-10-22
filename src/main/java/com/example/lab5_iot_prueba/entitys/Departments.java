package com.example.lab5_iot_prueba.entitys;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name = "departments")
public class Departments {
    @Id
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "manager_id")
    private Integer managerId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations locationId;
}
