package com.example.lab5_iot;

import android.os.Bundle;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrincipalBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(PrincipalFragment.this);

        binding.buttonIrATutor.setOnClickListener(view -> {
            navController.navigate(R.id.action_principalFragment_to_tutorFragment);//hacia la vista del tutor

        });

        binding.buttonIrATrabajador.setOnClickListener(view -> {
            navController.navigate(R.id.action_principalFragment_to_trabajadorFragment); //hacia la vista del trabajador
        });


        return binding.getRoot();



    }
}