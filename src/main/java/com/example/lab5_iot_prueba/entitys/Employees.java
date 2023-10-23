package com.example.lab5_iot_prueba.entitys;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employees {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "employee_id")
    private Integer employeeId;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;


    @Column(name = "email")
    private String email;


    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "hire_date")
    private Timestamp hireDate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs jobId;


    @Column(name = "salary")
    private BigDecimal salary;


    @Column(name = "commission_pct")
    private BigDecimal commissionPct;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employees managerId;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Departments departmentId;


    @Column(name = "meeting")
    private Integer meeting;


    @Column(name = "meeting_date")
    private Timestamp meetingDate;

    @Column(name = "employee_feedback")
    private String employeeFeedback;
}
