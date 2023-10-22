package com.example.lab5_iot_prueba.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "job_history")
@Entity
public class JobHistory {

    @EmbeddedId
    private JobHistoryId jobHistoryIdEmbedded;

    @MapsId("startDate")
    @JoinColumn(name = "start_date")
    private Timestamp startDate;

    @JoinColumn(name = "end_date")
    private Timestamp endDate;

    @MapsId("employeeId")
    @ManyToOne
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employees employeeId;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs jobId;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Departments departmentId;

}


