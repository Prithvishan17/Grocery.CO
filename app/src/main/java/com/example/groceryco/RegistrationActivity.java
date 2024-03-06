package com.example.groceryco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {


    TextView loginButton;
    EditText mName, mEmail, mPassword;
    Button btnRegister;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mName = findViewById(R.id.et_name);
        mPassword = findViewById(R.id.et_password);
        mEmail = findViewById(R.id.et_email);
        btnRegister = findViewById(R.id.btn_register);
        loginButton = findViewById(R.id.tv_acc_login);
        progressBar = new ProgressBar(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth();

            }
        });
    }

    private void mAuth() {
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            mName.setError("Name is Required");
            return;
        }

        if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required");
            return;
        }

        if (password.isEmpty() || password.length()<6){
            mPassword.setError("Password not Match the Requirements");
            return;
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
        }

        //Register the user in firebase
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(RegistrationActivity.this, "User Successfully Created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}