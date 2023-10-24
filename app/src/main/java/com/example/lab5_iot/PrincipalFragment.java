package com.example.lab5_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.databinding.FragmentDescargarListaTrabajadoresBinding;
import com.example.lab5_iot.databinding.FragmentPrincipalBinding;


public class PrincipalFragment extends Fragment {

    FragmentPrincipalBinding binding;

    String idcanal1 = "channelHighPriorityTutor";
    String idcanal2 = "channelHighPriorityTrabajador";

    String mensaje = "Canal para notificaciones High Priority";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrincipalBinding.inflate(inflater, container, false);


        //crearChannelNotificationTrabajador();

        NavController navController = NavHostFragment.findNavController(PrincipalFragment.this);

        binding.buttonIrATutor.setOnClickListener(view -> {
            navController.navigate(R.id.action_principalFragment_to_tutorFragment);//hacia la vista del tutor

        });

        binding.buttonIrATrabajador.setOnClickListener(view -> {
            navController.navigate(R.id.action_principalFragment_to_trabajadorFragment); //hacia la vista del trabajador
        });


        return binding.getRoot();



    }


    /*public void crearChannelNotificationTutor(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(idcanal1,  mensaje, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("HIGH PRIORITY");
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            askPermission(); //preguntando permisos

        }
    }*/


    /*public void crearChannelNotificationTrabajador(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(idcanal2,  mensaje, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("HIGH PRIORITY");
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            askPermission(); //preguntando permisos

        }
    }*/

    /*public void askPermission(){
        //android.os.Build.VERSION_CODES.TIRAMISU == 33
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(getContext(), POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }*/
}