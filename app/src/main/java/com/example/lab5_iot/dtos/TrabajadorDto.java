package com.example.lab5_iot.dtos;

import com.example.lab5_iot.entity.Employee;

public class TrabajadorDto {

    private Employee employee;
    private String respuesta;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
