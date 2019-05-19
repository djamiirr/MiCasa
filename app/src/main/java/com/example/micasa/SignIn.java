package com.example.micasa;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.micasa.Models.Entities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText emailET;
    private EditText passwdET;
    private Button login, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        //init views
        initViews();

        //init listeners
        initListeners();

        //init firebase
        initFirebase();

    }

    private void initFirebase() {
        //firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    private void initListeners() {
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmailPasswd();
            }

        });
    }

    private void loginEmailPasswd() {
        //loading dialog...
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //start signin
        auth.signInWithEmailAndPassword(emailET.getText().toString(), passwdET.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //when signin completed

                //closing loading dialog...
                progressDialog.dismiss();

                if (task.isSuccessful()){
                    if (task.getResult().getUser() != null) {

                        Entities.USER = task.getResult().getUser();
                        startActivity(new Intent(SignIn.this, MainActivity.class));
                    }else {

                        Toast.makeText(SignIn.this, "Verifier votre login/mot de passe", Toast.LENGTH_LONG);
                    }
                }else {
                    Toast.makeText(SignIn.this, "Verifier votre connexion internet", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void initViews() {
        passwdET = findViewById(R.id.editTextPassword1);
        emailET = findViewById(R.id.editTextEmail1);
        login = findViewById(R.id.btnsigin);
        signUp = findViewById(R.id.btnsignup1);
    }
}
