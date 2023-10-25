package com.example.lab5_iot.fragmentosTutor;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab5_iot.api.TrabajadorService;
import com.example.lab5_iot.databinding.FragmentBuscarTrabajadorBinding;
import com.example.lab5_iot.dtos.TrabajadorDto;
import com.example.lab5_iot.entity.Department;
import com.example.lab5_iot.entity.Employee;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BuscarTrabajadorFragment extends Fragment {

    FragmentBuscarTrabajadorBinding binding;

    String idTrabajador;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBuscarTrabajadorBinding.inflate(inflater, container, false);
        TrabajadorService trabajadorService = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")   //ip emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrabajadorService.class);
        binding.buttonDescargarInfoTrabajador.setOnClickListener(view -> {
            idTrabajador = binding.textCodigoTrabajador.getText().toString();
            if (!idTrabajador.isEmpty()){
                //int id = Integer.parseInt(idTrabajador);
                trabajadorService.buscarTrabajadorPorId(idTrabajador).enqueue(new Callback<TrabajadorDto>() {
                    @Override
                    public void onResponse(Call<TrabajadorDto> call, Response<TrabajadorDto> response) {
                        if (response.isSuccessful()){

                            TrabajadorDto trabajadorDto = response.body();

                            guardarArchivoComoJson(trabajadorDto.getEmployee());

                            Toast.makeText(getContext(), "Se descargó correctamente la informacion del trabajador", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<TrabajadorDto> call, Throwable t) {
                        Log.d("msg-test","Algo paso");
                        Log.d("msg-test",t.getMessage());
                    }
                });


            } else {

                Toast.makeText(getContext(), "Por favor ingrese un ID ", Toast.LENGTH_SHORT).show();
                Log.d("msg-test","Algo paso codigo");
                Log.d("msg-test", idTrabajador);
            }
        });

        return binding.getRoot();
    }


    public void guardarArchivoComoJson(Employee employee) {
        //convertimos el arreglo a un String (para guardarlo como json)

        Gson gson = new Gson();
        String listaEmployeeJson = gson.toJson(employee);

        //nombre del archivo a guardar
        String fileNameJson = "informacionDe" +employee.getEmployeeId().toString() + ".txt";

        //Se utiliza la clase FileOutputStream para poder almacenar en Android
        try (FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            fileWriter.write(listaEmployeeJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}