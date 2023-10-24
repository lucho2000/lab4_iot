package com.example.lab5_iot.dtos;

import com.example.lab5_iot.entity.Employee;

public class TrabajadorDto {

    private Employee employee;
    private String result;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
