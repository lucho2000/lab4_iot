package com.example.lab5_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.api.TrabajadorService;
import com.example.lab5_iot.databinding.FragmentTrabajadorBinding;
import com.example.lab5_iot.dtos.TrabajadorDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TrabajadorFragment extends Fragment {

    FragmentTrabajadorBinding binding;

    String canal2 = "canalHighPriority";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTrabajadorBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(TrabajadorFragment.this);

        binding.buttonDescargarHorarios.setOnClickListener(view -> {
            navController.navigate(R.id.action_trabajadorFragment_to_descargarHorariosFragment);
        });

        binding.buttonFeedback.setOnClickListener(view -> {

        });

        lanzarNotificacion();


        TrabajadorDto dto = new TrabajadorDto();
        TrabajadorService trabajadorService = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")   //ip emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrabajadorService.class);

        //trabajadorService.

        lanzarNotificacion2(dto.getEmployee().getMeetingDate());
        return binding.getRoot();
    }



    public void lanzarNotificacion(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), canal2)
                .setSmallIcon(R.drawable.baseline_groups_24)
                .setContentTitle("Trabajador")
                .setContentText("Está entrando en modo Trabajador")
                .setPriority(NotificationCompat.PRIORITY_HIGH) //alta prioridad
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(1, builder.build());
        }
    }

    public void lanzarNotificacion2(String fechaHora)  {


        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = formato.parse(fechaHora);

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

            String fecha = formatoFecha.format(date); // Obtiene la fecha
            String hora = formatoHora.format(date);

            Intent intent = new Intent(getActivity(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), canal2)
                    .setSmallIcon(R.drawable.baseline_groups_24)
                    .setContentTitle("Tutorias pendientes")
                    .setContentText(fecha + " "+ hora) //para mostrar la fecha en el contenido
                    .setPriority(NotificationCompat.PRIORITY_HIGH) //alta prioridad
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
            if (ActivityCompat.checkSelfPermission(getActivity(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                notificationManagerCompat.notify(1, builder.build());
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }




    }
}