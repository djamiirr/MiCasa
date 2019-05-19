package com.example.micasa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.micasa.Models.Entities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Fan extends Fragment {

    private String lightsEnabled = "low";
    private SwitchCompat switchCompat;

    public Fan() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_lights,container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchCompat = view.findViewById(R.id.switch1);
        final ImageView imageView= view.findViewById(R.id.img1);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.fanclosed));

        initFirebaseGazonValueListener();

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchCompat.isChecked()){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.fanopned));

                }else{
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.fanclosed));

                }
                Entities.REFERENCE.child("light").setValue(isChecked ? "high" : "low");

            }
        });
    }

    private void initFirebaseGazonValueListener() {
        //pour recuperer la valeur de "light"
        Entities.REFERENCE.child("light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    lightsEnabled = dataSnapshot.getValue(String.class);
                    updateSwitcher();
                }catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateSwitcher() {
        switchCompat.setChecked(lightsEnabled == "low" ? false : true);
    }



}


