package com.example.lab5_iot_prueba.services;


import com.example.lab5_iot_prueba.entitys.Employees;
import com.example.lab5_iot_prueba.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController()
@RequestMapping("/tutor")
public class TutorWebService {

    @Autowired
    EmployeesRepository employeesRepository;


    @GetMapping("/{tutor_id}/trabajadores")
    public ResponseEntity<HashMap<String, Object>> obtenerListaTrabajoresPorTutorId(@PathVariable("tutor_id") String tutor_id_str) {
        HashMap<String,Object> responseJson = new HashMap<>();
        try {

            Optional<Employees> optionalEmployees = employeesRepository.findById(Integer.parseInt(tutor_id_str));
            if (optionalEmployees.isPresent()){
                responseJson.put("result","success");
                List<Employees> listaEmployeesPorTutor = employeesRepository.obtenerListaTrabajadoresPorTutorId(tutor_id_str);
                responseJson.put("employees",listaEmployeesPorTutor);
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("msg","Tutor no existente");
            }
        } catch (NumberFormatException e) {
            responseJson.put("mgs", "el id debe ser un numero entero positivo");

        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);
    }



}
