package com.example.lab5_iot.fragmentosTutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.R;
import com.example.lab5_iot.api.TrabajadorService;
import com.example.lab5_iot.databinding.FragmentAsignarTutoriaBinding;
import com.example.lab5_iot.dtos.RespuestaAsignarTutoriaDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AsignarTutoriaFragment extends Fragment {

    FragmentAsignarTutoriaBinding binding;

    String idTutor, idTrabajador;

    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")   //ip emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAsignarTutoriaBinding.inflate(inflater, container, false);

        idTutor = binding.textCodigoTutor.getText().toString();
        idTrabajador = binding.textCodigoTrabaja.getText().toString();

        if ((!idTutor.equals("")) && (!idTrabajador.equals(""))){

            trabajadorService.asignarTutoria(idTrabajador, idTutor).enqueue(new Callback<RespuestaAsignarTutoriaDto>() {
                @Override
                public void onResponse(Call<RespuestaAsignarTutoriaDto> call, Response<RespuestaAsignarTutoriaDto> response) {
                    if (response.isSuccessful()){
                        RespuestaAsignarTutoriaDto respuesta = response.body();

                    }else {

                    }
                }

                @Override
                public void onFailure(Call<RespuestaAsignarTutoriaDto> call, Throwable t) {
                    t.printStackTrace();
                }
            });


        }



        return binding.getRoot();
    }
}