package com.example.lab5_iot.api;

import com.example.lab5_iot.dtos.ListaTrabajadoresDto;
import com.example.lab5_iot.dtos.RespuestaAsignarTutoriaDto;
import com.example.lab5_iot.dtos.TrabajadorDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

//trabajador = employees
public interface TrabajadorService {

    @GET("/trabajador/info")
    Call<TrabajadorDto> buscarTrabajadorPorId(@Query("id") int idTrabajador);

    @GET("/trabajador/verTodos")
    Call<ListaTrabajadoresDto> buscarTodosTrabajadores();

    @PUT("/tutor/asignarTutoria")
    Call<RespuestaAsignarTutoriaDto> asignarTutoria(@Query("idEmployee") int idTrabajador, @Query("idTutor") int idTutor);

}
