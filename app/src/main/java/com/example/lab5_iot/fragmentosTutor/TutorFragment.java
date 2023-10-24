package com.example.lab5_iot.fragmentosTutor;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

import com.example.lab5_iot.MainActivity;
import com.example.lab5_iot.R;
import com.example.lab5_iot.databinding.FragmentPrincipalBinding;
import com.example.lab5_iot.databinding.FragmentTutorBinding;

public class TutorFragment extends Fragment {

    FragmentTutorBinding binding;

    String canal1 = "canalHighPriority";

    String idcanal1 = "channelHighPriorityTutor";

    String mensaje = "Canal para notificaciones High Priority";

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       binding = FragmentTutorBinding.inflate(inflater, container, false);


       NavController navController = NavHostFragment.findNavController(TutorFragment.this);

       binding.buttonDescargarListaTrabajadores.setOnClickListener(view -> {
            navController.navigate(R.id.action_tutorFragment_to_descargarListaTrabajadoresFragment2);

       });


       binding.buttonBuscarTrabajador.setOnClickListener(view -> {
           navController.navigate(R.id.action_tutorFragment_to_buscarTrabajadorFragment);
       });

       binding.buttonAsignarTutoria.setOnClickListener(view -> {
           navController.navigate(R.id.action_tutorFragment_to_asignarTutoriaFragment);
       });

       return binding.getRoot();
    }

}