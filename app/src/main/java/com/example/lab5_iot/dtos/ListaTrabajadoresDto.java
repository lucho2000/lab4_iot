package com.example.lab5_iot.dtos;

import com.example.lab5_iot.entity.Employee;

import java.util.List;

public class ListaTrabajadoresDto {

    private List<Employee> listaEmpleado;

    private String respuesta;

    public List<Employee> getListaEmpleado() {
        return listaEmpleado;
    }

    public void setListaEmpleado(List<Employee> listaEmpleado) {
        this.listaEmpleado = listaEmpleado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
