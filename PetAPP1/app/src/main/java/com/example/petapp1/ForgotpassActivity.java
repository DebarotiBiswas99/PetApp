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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotpassActivity extends AppCompatActivity {

    EditText fpmail;
    Button fpsend;
    ImageView backbtn;
    private FirebaseAuth mAuth;
    String admin_email;
    String allowed;
    private ProgressDialog mdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        fpmail = findViewById(R.id.email_fp);
        fpsend = findViewById(R.id.fp_button);
        mAuth = FirebaseAuth.getInstance();
        mdialog = new ProgressDialog(this);

        fpsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdialog.setMessage("processing");
                mdialog.show();
                final String fpemail = fpmail.getText().toString().trim();
                //check if email is empty
                if (TextUtils.isEmpty(fpemail)) {
                    fpmail.setError("Required Field");
                    Toast.makeText(getApplicationContext(), "Enter Proper Email", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(fpemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Mail has been sent to your mail id to reset your Password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));


                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), "Error Occured" + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}



