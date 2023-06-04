package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petapp1.models.user_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    Button register;
    EditText name,email,phone,password;
    TextView goto_login;
    ImageView backbtn;
    private FirebaseAuth mAuth;
    ProgressDialog mdialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


    mDatabase = FirebaseDatabase.getInstance().getReference("/user");

    mdialog  = new ProgressDialog(RegistrationActivity.this);
        mdialog.setTitle("register");
     mdialog.setMessage("Registering Please wait");

    name = findViewById(R.id.register_name);
    email = findViewById(R.id.register_email);
    phone = findViewById(R.id.register_phone);
    password = findViewById(R.id.register_password);
    goto_login = findViewById(R.id.register_to_login);
    register = findViewById(R.id.register);


    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String user_email = email.getText().toString();
            String user_password = password.getText().toString();
            final String user_name = name.getText().toString();
            final String user_phone = phone.getText().toString();
            //condition to check if empty
            if(user_email.length()==0 || user_name.length()==0 || user_password.length()==0 || user_phone.length()==0){

                Toast.makeText(getApplicationContext(),"all fields Are compulsory", Toast.LENGTH_SHORT).show();
            }else{
                mdialog.show();
                //create user with firebase
                mAuth.createUserWithEmailAndPassword(user_email,user_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mAuth.getCurrentUser().getUid();
                        String id =  mAuth.getCurrentUser().getUid();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        //set Users display name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(user_name)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //add success message
                                        }
                                    }
                                });
                        //sending data to user node
                        user_data data = new user_data(user_name, user_phone, id, user_email);
                        mDatabase.child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                                mdialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("insertion error","error "+e);
                                mdialog.dismiss();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mdialog.dismiss();
                        Toast.makeText(getApplicationContext(),"problem"+e,Toast.LENGTH_SHORT).show();
                    }
                });





            }
        }
    });

        goto_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    });


}
}
