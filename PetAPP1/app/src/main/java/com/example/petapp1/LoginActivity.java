package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText email,password;
    TextView goto_register , fpassword;
    ImageView backbtn;
    private FirebaseAuth mAuth;
    ProgressDialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialise the dialog box
        mdialog  = new ProgressDialog(LoginActivity.this);
        mdialog.setTitle("Logging in");
        mdialog.setMessage("Logging In Please wait");


        //initialising views
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.login_button);
        goto_register = findViewById(R.id.login_to_register);
        fpassword = findViewById(R.id.forgot_password);

        //initialising mauth
        mAuth = FirebaseAuth.getInstance();

        //onclick listener for login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memail = email.getText().toString().trim();
                String mpass = password.getText().toString().trim();
                if (TextUtils.isEmpty(memail)) {
                    email.setError("required Field...");
                    return;
                } else if (TextUtils.isEmpty(mpass)) {
                    password.setError("Required Field");
                    return;
                } else {
                    mdialog.setMessage("Processing..");
                    mdialog.show();
                    //Sign in with email and password
                    mAuth.signInWithEmailAndPassword(memail, mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mdialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(getApplicationContext(), "login complete", Toast.LENGTH_SHORT).show();
                            } else {
                                mdialog.dismiss();
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }

        });



        goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),splash_2.class));
//            }
//        });

        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotpassActivity.class));
            }
        });
    }
}