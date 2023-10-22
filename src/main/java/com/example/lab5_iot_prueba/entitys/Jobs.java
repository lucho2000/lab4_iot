package com.example.lab5_iot_prueba.entitys;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name="jobs")
public class Jobs {
    @Id
    @Column(name = "job_id")
    private String jobId;


    @Column(name = "job_title")
    private String jobTitle;


    @Column(name = "min_salary")
    private Integer minSalary;


    @Column(name = "max_salary")
    private Integer maxSalary;
}

