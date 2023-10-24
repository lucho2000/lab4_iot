package com.example.lab5_iot.api;

import com.example.lab5_iot.dtos.EmployeeFeedback;
import com.example.lab5_iot.dtos.ListaTrabajadoresDto;
import com.example.lab5_iot.dtos.RespuestaAsignarTutoriaDto;
import com.example.lab5_iot.dtos.TrabajadorDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

//trabajador = employees
public interface TrabajadorService {

    @GET("/tutor/trabajadores/{trabajador_id}")
    Call<TrabajadorDto> buscarTrabajadorPorId(@Path("trabajador_id") String idTrabajador);  // ruta web service: tutor/trabajadores/{trabajador_id}

    @GET("/tutor/{tutor_id}/trabajadores")
    Call<ListaTrabajadoresDto> buscarTodosTrabajadores(@Path("tutor_id") String idTutor);  // tutor/{tutor_id}/trabajadores


    //  /{tutor_id}/trabajador/{trabajador_id}
    @PUT("/{tutor_id}/trabajador/{trabajador_id}")
    Call<RespuestaAsignarTutoriaDto> asignarTutoria(@Path("trabajador_id") String idTrabajador, @Path("tutor_id") String idTutor);

    @PUT("/{trabajador_id}/feedback")
    Call<EmployeeFeedback> enviarFeedback(@Path("trabajador_id") String idTrabajador, @Query("meeting_feedback") String feedback);

}
