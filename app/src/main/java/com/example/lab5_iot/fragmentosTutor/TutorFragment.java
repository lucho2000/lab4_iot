package com.example.lab5_iot.fragmentosTutor;

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

import com.example.lab5_iot.MainActivity;
import com.example.lab5_iot.R;
import com.example.lab5_iot.databinding.FragmentPrincipalBinding;
import com.example.lab5_iot.databinding.FragmentTutorBinding;

public class TutorFragment extends Fragment {

    FragmentTutorBinding binding;

    String canal1 = "canalHighPriority";

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       binding = FragmentTutorBinding.inflate(inflater, container, false);

       lanzarNotificacion();
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



    public void lanzarNotificacion(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), canal1)
                .setSmallIcon(R.drawable.baseline_groups_24)
                .setContentTitle("Tutor")
                .setContentText("Está entrando en modo Tutor")
                .setPriority(NotificationCompat.PRIORITY_HIGH) //alta prioridad
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(1, builder.build());
        }
    }
}