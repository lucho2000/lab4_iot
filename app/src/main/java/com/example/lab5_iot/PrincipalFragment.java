package com.example.lab5_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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

import com.example.lab5_iot.databinding.FragmentDescargarListaTrabajadoresBinding;
import com.example.lab5_iot.databinding.FragmentPrincipalBinding;


public class PrincipalFragment extends Fragment{

    FragmentPrincipalBinding binding;

    String idcanal1 = "channelHighPriorityTutor";
    String idcanal2 = "channelHighPriorityTrabajador";

    String mensaje = "Canal para notificaciones High Priority";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrincipalBinding.inflate(inflater, container, false);


        NavController navController = NavHostFragment.findNavController(PrincipalFragment.this);

        binding.buttonIrATutor.setOnClickListener(view -> {
            navController.navigate(R.id.action_principalFragment_to_tutorFragment);//hacia la vista del tutor

            crearChannelNotificationTutor();
            lanzarNotificacionTutor();

        });

        binding.buttonIrATrabajador.setOnClickListener(view -> {
            navController.navigate(R.id.action_principalFragment_to_trabajadorFragment); //hacia la vista del trabajador

            crearChannelNotificationTrabajador();
            lanzarNotificacion2();
        });


        return binding.getRoot();

    }


    public void askPermission(){
        //android.os.Build.VERSION_CODES.TIRAMISU == 33
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(getContext(), POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }


    public void crearChannelNotificationTutor(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(idcanal1,  mensaje, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("HIGH PRIORITY");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            askPermission(); //preguntando permisos

        }
    }



    public void lanzarNotificacionTutor(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), idcanal1)
                .setSmallIcon(R.drawable.baseline_groups_24)
                .setContentTitle("Tutor")
                .setContentText("Está entrando en modo Tutor")
                .setPriority(NotificationCompat.PRIORITY_HIGH) //alta prioridad
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(1, builder.build());
        }
    }

    public void crearChannelNotificationTrabajador(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(idcanal2,  mensaje, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("HIGH PRIORITY");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            askPermission(); //preguntando permisos

        }
    }


    public void lanzarNotificacion2(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), idcanal2)
                .setSmallIcon(R.drawable.baseline_groups_24)
                .setContentTitle("Trabajador")
                .setContentText("Está entrando en modo Trabajador")
                .setPriority(NotificationCompat.PRIORITY_HIGH) //alta prioridad
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(2, builder.build());
        }
    }

}