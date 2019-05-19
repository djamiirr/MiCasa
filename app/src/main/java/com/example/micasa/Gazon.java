package com.example.micasa;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.micasa.Models.Entities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class Gazon extends Fragment {

    private boolean gazonEnabled = false;
    private SwitchCompat switchCompat;

    public Gazon() {
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
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.gazonoff));

        //init firebase gazon value listener
        initFirebaseGazonValueListener();
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.gazonon));

                }else{
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.gazonoff));
                }

                Entities.REFERENCE.child("gazon").setValue(isChecked);
            }
        });
    }

    private void initFirebaseGazonValueListener() {
        Entities.REFERENCE.child("gazon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    gazonEnabled = dataSnapshot.getValue(Boolean.class);
                    updateSwitcher();
                }catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateSwitcher() {
        switchCompat.setChecked(gazonEnabled);
    }


}


