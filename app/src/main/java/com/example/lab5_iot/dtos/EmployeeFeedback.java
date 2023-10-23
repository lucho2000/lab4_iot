package com.example.lab5_iot.dtos;

import com.example.lab5_iot.entity.Employee;

public class EmployeeFeedback {

    private Employee employee;

    private String feedback;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
