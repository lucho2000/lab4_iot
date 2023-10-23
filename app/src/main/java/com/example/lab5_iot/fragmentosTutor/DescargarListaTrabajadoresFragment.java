package com.example.lab5_iot.fragmentosTutor;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab5_iot.api.TrabajadorService;
import com.example.lab5_iot.databinding.FragmentDescargarListaTrabajadoresBinding;
import com.example.lab5_iot.dtos.ListaTrabajadoresDto;
import com.example.lab5_iot.entity.Employee;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescargarListaTrabajadoresFragment extends Fragment {


    FragmentDescargarListaTrabajadoresBinding binding;

    String codigoTutor;
    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")   //ip emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentDescargarListaTrabajadoresBinding.inflate(inflater, container, false);

        codigoTutor = binding.textCodigoTutor.getText().toString();


        binding.buttonDescargarListaTrabajadores.setOnClickListener(view -> {

            if (!codigoTutor.isEmpty()) {

                int idTutor = Integer.parseInt(codigoTutor);

                trabajadorService.buscarTodosTrabajadores(codigoTutor).enqueue(new Callback<ListaTrabajadoresDto>() {
                    @Override
                    public void onResponse(Call<ListaTrabajadoresDto> call, Response<ListaTrabajadoresDto> response) {

                        if (response.isSuccessful()){
                            ListaTrabajadoresDto listaTrabajadoresDto = response.body();
                            HashMap<String, HashMap<String, Object>> datos = obtenerDatos(listaTrabajadoresDto.getListaEmpleado());
                            guardarArchivoComoJson2(datos);

                            Toast.makeText(getContext(), "Se descargó correctamente la lista de trabajadores", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ListaTrabajadoresDto> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        return binding.getRoot();

    }

    public HashMap<String, HashMap<String, Object>> obtenerDatos(List<Employee> employees) {
        HashMap<String, HashMap<String, Object>> diccionario = new HashMap<>();

        for (int i = 0; i < employees.size(); i++) {
            HashMap<String, Object> data = new HashMap<>();

            data.put("employeeId", employees.get(i).getEmployeeId().toString());
            String firstName = employees.get(i).getFirstName();

            if (firstName != null || !firstName.equals(""))
                data.put("firstName", employees.get(i).getEmployeeId().toString());
            else
                data.put("firstName", "sin nombre");

            data.put("lastName", employees.get(i).getLastName());
            data.put("email", employees.get(i).getEmail());

            if (employees.get(i).getPhoneNumber() != null || employees.get(i).getPhoneNumber().equals(""))
                data.put("phoneNumber", employees.get(i).getPhoneNumber().toString());
            else
                data.put("phoneNumber", "no tiene");

            data.put("jobTitle", employees.get(i).getJobId().getJobTitle().toString());

            //salario
            if (employees.get(i).getSalary()!= null)
                data.put("salary", String.valueOf(employees.get(i).getSalary()));
            else
                data.put("salary", "no tiene");


            if (employees.get(i).getDepartmentId() == null) {
                data.put("departmentName", "no tiene");
                data.put("countryName", "no tiene");
                data.put("region", "no tiene");
            } else {
                data.put("departmentName", employees.get(i).getDepartmentId().getDepartmentName());

                if (employees.get(i).getDepartmentId().getLocationId() == null) {
                    data.put("countryName", "no tiene");
                    data.put("regionName", "no tiene");
                } else {

                    if (employees.get(i).getDepartmentId().getLocationId().getCountryId() == null) {
                        data.put("countryName", "no tiene");
                        data.put("regionName", "no tiene");
                    } else {
                        data.put("countryName", employees.get(i).getDepartmentId().getLocationId().getCountryId().getCountryName());

                        if (employees.get(i).getDepartmentId().getLocationId().getCountryId().getRegionsId() == null) {
                            data.put("regionName", "no tiene");
                        } else {
                            data.put("regionName", employees.get(i).getDepartmentId().getLocationId().getCountryId().getRegionsId().getRegionName());
                        }
                    }
                }
            }
            diccionario.put("employee" + String.valueOf(i), data);
        }
        return diccionario;
    }

    public void guardarArchivoComoJson2(HashMap<String, HashMap<String, Object>> listaEmployeesPorManager) {
        //convertimos el arreglo a un String (para guardarlo como json)

        //String idTrabajadorStr = (String) listaEmployee.get("id");
        Gson gson = new Gson();
        String listaEmployeeJson = gson.toJson(listaEmployeesPorManager);

        //nombre del archivo a guardar
        String fileNameJson = "listaTrabajadores.txt";

        //Se utiliza la clase FileOutputStream para poder almacenar en Android
        try (FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            fileWriter.write(listaEmployeeJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}