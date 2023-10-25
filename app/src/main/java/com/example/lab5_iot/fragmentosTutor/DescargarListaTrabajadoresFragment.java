package com.example.lab5_iot.fragmentosTutor;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
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

import java.io.File;
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

    String fileNameJson = "listaTrabajadores.txt";


    ListaTrabajadoresDto dto;

    ActivityResultLauncher<String> launcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentDescargarListaTrabajadoresBinding.inflate(inflater, container, false);

        TrabajadorService trabajadorService = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")   //ip emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrabajadorService.class);

        binding.buttonDescargarListaTrabajadores.setOnClickListener(view -> {
            codigoTutor = binding.textCodigoTutor.getText().toString();

            if (!codigoTutor.isEmpty()) {

                trabajadorService.buscarTodosTrabajadores(codigoTutor).enqueue(new Callback<ListaTrabajadoresDto>() {
                    @Override
                    public void onResponse(Call<ListaTrabajadoresDto> call, Response<ListaTrabajadoresDto> response) {

                        if (response.isSuccessful()){
                            ListaTrabajadoresDto listaTrabajadoresDto = response.body();

                            guardarArchivoComoJson2(listaTrabajadoresDto.getEmployees());
                            //descargarListaConDownloadManager(fileNameJson);

                            Toast.makeText(getContext(), "Se descargó correctamente la lista de trabajadores", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ListaTrabajadoresDto> call, Throwable t) {
                        Log.d("msg-test","Algo paso");
                        Log.d("msg-test",t.getMessage());
                    }
                });
            } else {
                //Log.d("msg-test","Algo paso codigo");
                //Log.d("msg-test", codigoTutor);
            }
        });

        return binding.getRoot();

    }


    public void guardarArchivoComoJson2(List<Employee> listaEmployeesPorManager) {
        //convertimos el arreglo a un String (para guardarlo como json)

        //String idTrabajadorStr = (String) listaEmployee.get("id");
        Gson gson = new Gson();
        String listaEmployeeJson = gson.toJson(listaEmployeesPorManager);

        //nombre del archivo a guardar
        //fileNameJson = "listaTrabajadores.txt";

        //Se utiliza la clase FileOutputStream para poder almacenar en Android
        try (FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            fileWriter.write(listaEmployeeJson);
            Log.d("msg-test","algo paso descargar");
            Log.d("msg-test", listaEmployeesPorManager.get(0).getFirstName());
        } catch (IOException e) {
            Log.d("msg-test", e.toString() );
        }
    }

    public void descargarListaConDownloadManager(String fileName) {


        if (Build.VERSION.SDK_INT >= 29 ||
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //si tengo permisos

            File archivoLocal = new File(getContext().getFilesDir(), "listaTrabajadores.txt");

            String ruta = getContext().getFilesDir() + "data/com.example.lab5_iot/files/listaTrabajadores.txt";

            Uri uri  = Uri.parse(ruta);
            //Uri archivoUri = FileProvider.getUriForFile(getContext(), "com.example.lab5_iot", archivoLocal);
            //String endPoint =  getContext().getFilesDir().getPath() + "/data/com.example.lab5_iot/files/" + fileName;

            //Uri downloadUri = Uri.parse(archivoUri);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle(fileName);
            request.setMimeType("text/plain");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + fileName);

            DownloadManager dm = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);
        } else {
            //si no tiene permisos
            launcher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }



}