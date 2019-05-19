package com.example.micasa;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.micasa.Models.Entities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {

    private EditText emailET, passwdET, editTextName, editTextFName;
    private Button signupBtn;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //init views
        initViews();


        //init listeners
        initListeners();

        //init firebase
        auth = FirebaseAuth.getInstance();
    }

    private void initListeners() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup() {
        auth.createUserWithEmailAndPassword(emailET.getText().toString(), passwdET.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //pour ajouter nom et prenom =>displayName
                    UserProfileChangeRequest.Builder profile = new UserProfileChangeRequest.Builder();
                    profile.setDisplayName(editTextName.getText().toString() + " " + editTextFName.getText().toString());
                    final FirebaseUser user = task.getResult().getUser();

                    //start updating => inserting display name to new user
                    user.updateProfile(profile.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Entities.USER = user;
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                        }
                    });
                }
            }
        });
    }

    private void initViews() {
        emailET = findViewById(R.id.editTextEmail1);
        editTextName = findViewById(R.id.editTextName);
        passwdET = findViewById(R.id.editTextPassword1);
        editTextFName = findViewById(R.id.editTextfName);
        signupBtn = findViewById(R.id.btnsignup1);
    }
}