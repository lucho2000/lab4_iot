package com.example.lab5_iot.dtos;

import com.example.lab5_iot.entity.Employee;

import java.util.List;

public class ListaTrabajadoresDto {

    private List<Employee> employees;

    private String result;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
