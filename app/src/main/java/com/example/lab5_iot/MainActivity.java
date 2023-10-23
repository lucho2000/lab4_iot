package com.example.lab5_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String idcanal1 = "channelHighPriorityTutor";
    String idcanal2 = "channelHighPriorityTrabajador";

    String mensaje = "Canal para notificaciones High Priority";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        crearChannelNotificationTutor();
        crearChannelNotificationTrabajador();

    }

    public void crearChannelNotificationTutor(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(idcanal1,  mensaje, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("HIGH PRIORITY");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            askPermission(); //preguntando permisos


        }
    }


    public void crearChannelNotificationTrabajador(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(idcanal2,  mensaje, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("HIGH PRIORITY");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            askPermission(); //preguntando permisos

        }
    }



    public void askPermission(){
        //android.os.Build.VERSION_CODES.TIRAMISU == 33
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }

}