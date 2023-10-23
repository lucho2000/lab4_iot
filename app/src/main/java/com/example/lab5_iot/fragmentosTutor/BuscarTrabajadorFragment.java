package com.example.lab5_iot.fragmentosTutor;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")   //ip emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBuscarTrabajadorBinding.inflate(inflater, container, false);


        idTrabajador = binding.textCodigoTrabajador.getText().toString();

        binding.buttonDescargarInfoTrabajador.setOnClickListener(view -> {

            if (!idTrabajador.isEmpty()){
                //int id = Integer.parseInt(idTrabajador);
                trabajadorService.buscarTrabajadorPorId(idTrabajador).enqueue(new Callback<TrabajadorDto>() {
                    @Override
                    public void onResponse(Call<TrabajadorDto> call, Response<TrabajadorDto> response) {
                        if (response.isSuccessful()){

                            TrabajadorDto trabajadorDto = response.body();
                            HashMap<String, Object> dataHM =  mostrarDataWebService(trabajadorDto.getEmployee());
                            guardarArchivoComoJson(dataHM);


                            Toast.makeText(getContext(), "Se descargó correctamente la informacion del trabajador", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<TrabajadorDto> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


            } else {

                Toast.makeText(getContext(), "Por favor ingrese un ID ", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }


    public HashMap<String, Object> mostrarDataWebService(Employee trabajador){

        HashMap<String, Object> data = new HashMap<>();

        data.put("id", trabajador.getEmployeeId().toString());

        String firstName = trabajador.getFirstName();
        String phoneNumber = trabajador.getPhoneNumber();
        Department idDepartment = trabajador.getDepartmentId();

        if (firstName.equals("") | firstName == null){

            data.put("Nombre", trabajador.getEmployeeId().toString());
        } else {
            data.put("Nombre", "sin nombre");
        }

        data.put("apellido", trabajador.getLastName());

        data.put("correo", trabajador.getEmail());

        //si tiene numero
        if (phoneNumber==null){
            data.put("Numero de telefono", "no tiene");
        } else {
            data.put("Numero de telefono", trabajador.getPhoneNumber() );
        }
        //job title
        data.put("job title", trabajador.getJobId().getJobTitle() );

        //salario
        if (trabajador.getSalary() == null){
            data.put("Salario", "-");
        } else {
            data.put("Salario", trabajador.getSalary().toString() );

        }

        if (idDepartment==null){ //validando si tiene id de departamento = area donde trabaja
            data.put("department", "no tiene");
            data.put("pais","no tiene" );
            data.put("region","no tiene" );
        } else {
            data.put("department", trabajador.getDepartmentId().getDepartmentName() );

            //validando si tiene id de location
            if (trabajador.getDepartmentId().getLocationId()==null){ //validando si tiene o no departamento = area donde trabaja
                data.put("pais","no tiene");
                data.put("region","no tiene");
            } else {

                //validar si tiene id de pais
                if (trabajador.getDepartmentId().getLocationId().getCountryId()==null){
                    data.put("pais","no tiene");
                } else {
                    data.put("pais", trabajador.getDepartmentId().getLocationId().getCountryId().getCountryName() );

                    //validar si tiene id de region
                    if (trabajador.getDepartmentId().getLocationId().getCountryId().getRegionsId()==null){
                        data.put("region","no tiene");
                    } else{
                        data.put("pais", trabajador.getDepartmentId().getLocationId().getCountryId().getRegionsId().getRegionName() );
                    }

                }

            }

        }

        return data;
    }


    public void guardarArchivoComoJson(HashMap<String, Object> employee) {
        //convertimos el arreglo a un String (para guardarlo como json)

        String idTrabajadorStr = (String) employee.get("id");
        Gson gson = new Gson();
        String listaEmployeeJson = gson.toJson(employee);

        //nombre del archivo a guardar
        String fileNameJson = "informacionDe" +idTrabajadorStr + ".txt";

        //Se utiliza la clase FileOutputStream para poder almacenar en Android
        try (FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            fileWriter.write(listaEmployeeJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}