package com.example.lab5_iot_prueba.repositories;

import com.example.lab5_iot_prueba.entitys.JobHistory;
import com.example.lab5_iot_prueba.entitys.JobHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHistoryRepository  extends JpaRepository<JobHistory, JobHistoryId> {

}
