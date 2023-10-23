package com.example.lab5_iot;


import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab5_iot.api.TrabajadorService;
import com.example.lab5_iot.databinding.FragmentDescargarHorariosBinding;
import com.example.lab5_iot.dtos.TrabajadorDto;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescargarHorariosFragment extends Fragment {

    FragmentDescargarHorariosBinding binding;

    String idTrabajador;

    String canal2 = "NotificationTrabajador";

    ActivityResultLauncher<String> launcher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDescargarHorariosBinding.inflate(inflater, container, false);

        TrabajadorService trabajadorService = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")   //ip emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrabajadorService.class);

        idTrabajador = binding.idTrabajador.getText().toString();


        //validando si el campo esta vacio
        if (!idTrabajador.isEmpty()){


            trabajadorService.buscarTrabajadorPorId(idTrabajador).enqueue(new Callback<TrabajadorDto>() {
                @Override
                public void onResponse(Call<TrabajadorDto> call, Response<TrabajadorDto> response) {
                    TrabajadorDto tDto = response.body();

                    if (response.isSuccessful()){

                        //si el id ingresado esta en la base de datos

                        if (idTrabajador == tDto.getEmployee().getEmployeeId().toString()){

                            //si tiene meetingId
                            if (tDto.getEmployee().getMeeting() ==1){
                                tDto.setRespuesta("tiene tutoria");

                                //se lanza el launcher con el metodo para descargar
                                launcher = registerForActivityResult(
                                        new ActivityResultContracts.RequestPermission(),
                                        isGranted -> {

                                            if (isGranted) { // permiso concedido
                                                descargarConDownloadManager();
                                            } else {
                                                Log.e("msg-test", "Permiso denegado");
                                            }
                                        });

                            } else{
                                Toast.makeText(getActivity(), "No cuenta con tutorias pendientes", Toast.LENGTH_SHORT).show();
                                lanzarNotificacion();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Ingrese un ID valido de trabajador", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<TrabajadorDto> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Ingrese el ID del trabajador", Toast.LENGTH_SHORT).show();
        }


        return binding.getRoot();
    }



    public void descargarConDownloadManager() {


        if (Build.VERSION.SDK_INT >= 29 ||
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //si tengo permisos
            String fileName = "horarios.jpg";
            String endPoint = "https://i.pinimg.com/564x/4e/8e/a5/4e8ea537c896aa277e6449bdca6c45da.jpg";

            Uri downloadUri = Uri.parse(endPoint);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle(fileName);
            request.setMimeType("image/jpeg");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + fileName);

            DownloadManager dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);
        } else {
            //si no tiene permisos
            launcher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    public void lanzarNotificacion(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), canal2)
                .setSmallIcon(R.drawable.baseline_groups_24)
                .setContentTitle("Tutorias - descargar horarios")
                .setContentText("No cuenta con tutorias pendientes")
                .setPriority(NotificationCompat.DEFAULT_ALL) //alta prioridad
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(1, builder.build());
        }
    }

}
