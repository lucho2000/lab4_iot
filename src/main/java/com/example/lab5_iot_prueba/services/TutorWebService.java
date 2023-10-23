package com.example.lab5_iot_prueba.services;


import com.example.lab5_iot_prueba.entitys.Employees;
import com.example.lab5_iot_prueba.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/trabajadores/{trabajador_id}")
    public ResponseEntity<HashMap<String, Object>> obtenerTrabajorPorId(@PathVariable("trabajador_id") String trabajador_id_str) {
        HashMap<String,Object> responseJson = new HashMap<>();
        try {

            Optional<Employees> optionalEmployees = employeesRepository.findById(Integer.parseInt(trabajador_id_str));
            if (optionalEmployees.isPresent()){
                responseJson.put("result","success");
                Employees employees = optionalEmployees.get();
                responseJson.put("employee",employees);
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("msg","Trabajador no existente");
            }
        } catch (NumberFormatException e) {
            responseJson.put("mgs", "el id debe ser un numero entero positivo");

        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);
    }

    @PutMapping(value = "/{tutor_id}/trabajador/{trabajador_id}")
    public ResponseEntity<HashMap<String, Object>> agendarMeetingTrabajador(@PathVariable("trabajador_id") String trabajador_id_str, @PathVariable("tutor_id") String tutor_id_str, @RequestParam("meeting_date") String meeting_date) {

        HashMap<String, Object> responseJson = new HashMap<>();
        //boolean meeting = false;
        try {
            Optional<Employees> optionalTutorEmployees = employeesRepository.findById(Integer.parseInt(tutor_id_str));
            Optional<Employees> optionalTrabajdorEmployees = employeesRepository.findById(Integer.parseInt(trabajador_id_str));
            if (optionalTutorEmployees.isPresent()){
                Employees tutor = optionalTutorEmployees.get();
                if (optionalTrabajdorEmployees.isPresent()) {
                    Employees trabajador = optionalTrabajdorEmployees.get();
                    //List<Employees> listaEmployeesPorTutor = employeesRepository.obtenerListaTrabajadoresPorTutorId(tutor_id_str);
                    Employees trabajadorManager = trabajador.getManagerId();

                    if (trabajadorManager != null  ) {
                        Integer trabajadorManagerId = trabajador.getManagerId().getEmployeeId() ;
                        Integer tutorId = tutor.getEmployeeId();
                        if (Objects.equals(trabajadorManagerId, tutorId)) {

                            if (trabajador.isMeeting()) {
                                //tiene cita
                                responseJson.put("msg","cita_already_exist");
                            } else {
                                //no tiene cita

                                employeesRepository.updateMeeting(meeting_date,trabajador_id_str);
                            /*
                            trabajador.setMeeting(true);

                            if (trabajadorEnviado.getMeetingDate() != null) {

                            }
                                trabajador.setEmail(trabajadorEnviado.getEmail());

                            if (trabajadorEnviado.getFirstName() != null)
                                trabajador.setFirstName(trabajadorEnviado.getFirstName());

                            if (trabajadorEnviado.getLastName() != null)
                                trabajador.setLastName(trabajadorEnviado.getLastName());


                            if (trabajadorEnviado.getPhoneNumber() != null)
                                trabajador.setPhoneNumber(trabajadorEnviado.getPhoneNumber());

                            if (trabajadorEnviado.getHireDate() != null)
                                trabajador.setHireDate(trabajadorEnviado.getHireDate());

                            if (trabajadorEnviado.getJobId() != null)
                                trabajador.setJobId(trabajadorEnviado.getJobId());
                            */

                                responseJson.put("result","success");
                                responseJson.put("msg","meeting created with employeeId: " + trabajador.getEmployeeId());
                                return ResponseEntity.ok(responseJson);
                            }

                        } else {
                            responseJson.put("msg","match_error");
                        }
                    } else {
                        responseJson.put("msg","trabajador no tiene manager/tutor");
                    }


                } else {
                    responseJson.put("msg","no_employee");
                }

            } else {
                responseJson.put("msg","no_tutor");
            }
        } catch (NumberFormatException e) {
            responseJson.put("mgs", "id_error");

        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);


    }




}
