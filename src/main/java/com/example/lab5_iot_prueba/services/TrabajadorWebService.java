package com.example.lab5_iot_prueba.services;

import com.example.lab5_iot_prueba.entitys.Employees;
import com.example.lab5_iot_prueba.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController()
@RequestMapping("/trabajador")
public class TrabajadorWebService {

    @Autowired
    EmployeesRepository employeesRepository;

    @GetMapping("/{trabajador_id}")
    public ResponseEntity<HashMap<String, Object>> obtenerTrabajorPorId(@PathVariable("trabajador_id") String trabajador_id_str) {
        HashMap<String,Object> responseJson = new HashMap<>();
        try {

            Optional<Employees> optionalEmployees = employeesRepository.findById(Integer.parseInt(trabajador_id_str));
            if (optionalEmployees.isPresent()){
                if (optionalEmployees.get().isMeeting()) {
                    //tiene tutoria
                    responseJson.put("result","success");
                    //Employees employees = optionalEmployees.get();
                    responseJson.put("tutoria_flag",true);   //debes evaluar la condicion de true or false para saber si tiene o no tuto
                    return ResponseEntity.ok(responseJson);  // tmb se podria enviar el employee completo y validarlo recien en android.
                } else {
                    //no tiene tutoria
                    responseJson.put("result","success");
                    responseJson.put("tutoria_flag",false);
                    return ResponseEntity.ok(responseJson);
                }

            } else {
                responseJson.put("msg","Trabajador no existente");
            }
        } catch (NumberFormatException e) {
            responseJson.put("mgs", "el id debe ser un numero entero positivo");

        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);
    }

    @PutMapping(value = "/{trabajador_id}/feedback")
    public ResponseEntity<HashMap<String, Object>> agendarMeetingTrabajador(@PathVariable("trabajador_id") String trabajador_id_str, @RequestParam("meeting_feedback") String meeting_feedback_str) {

        HashMap<String, Object> responseJson = new HashMap<>();
        //boolean meeting = false;
        try {

            Optional<Employees> optionalTrabajdorEmployees = employeesRepository.findById(Integer.parseInt(trabajador_id_str));
                if (optionalTrabajdorEmployees.isPresent()) {
                    Employees trabajador = optionalTrabajdorEmployees.get();

                    if (trabajador.isMeeting()  ) {


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
                        employeesRepository.updateMeetingFeedback(meeting_feedback_str,trabajador_id_str);
                        responseJson.put("result","success");
                        responseJson.put("msg","meeting feedback updated with employeeId: " + trabajador.getEmployeeId());
                        return ResponseEntity.ok(responseJson);

                    } else {
                        responseJson.put("msg","tutoria_no_tuvo");
                    }
                } else {
                    responseJson.put("msg","no_employee");
                }

        } catch (NumberFormatException e) {
            responseJson.put("mgs", "id_error");

        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);


    }

}
