package com.example.lab5_iot_prueba.entitys;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Embeddable
public class JobHistoryId implements Serializable {

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "employee_id")
    private Integer employeeId;

}
