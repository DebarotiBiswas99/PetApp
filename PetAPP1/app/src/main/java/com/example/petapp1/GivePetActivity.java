package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petapp1.models.givepet_data;
import com.example.petapp1.models.givepet_data;
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

public class GivePetActivity extends AppCompatActivity {


    FirebaseUser currentUser;
    Button givepetapply;
    EditText breed, age, weight, ownername, phone, address, city, state, country, pincode;
    ImageView backbtn;
    Spinner pettype, gender;
    FirebaseAuth mAuth;
    ProgressDialog mdialog;
    DatabaseReference mDatabase;
    String petis ="";
    String[] type = { "Select Type", "Cat", "Dog" };
    String genderis ="";
    String[] gen = {"Select Gender", "Male", "Female"};

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_pet);


        mDatabase = FirebaseDatabase.getInstance().getReference("/givepet");

        mdialog = new ProgressDialog(GivePetActivity.this);
        mdialog.setTitle("Register");
        mdialog.setMessage("Registering Please wait");

        pettype = findViewById(R.id.givepet_pettype);
        breed = findViewById(R.id.givepet_breed);
        age = findViewById(R.id.givepet_age);
        weight = findViewById(R.id.givepet_weight);
        gender = findViewById(R.id.givepet_gender);
        ownername = findViewById(R.id.givepet_ownername);
        phone = findViewById(R.id.givepet_phone);
        address = findViewById(R.id.givepet_address);
        city = findViewById(R.id.givepet_city);
        state = findViewById(R.id.givepet_state);
        country = findViewById(R.id.givepet_country);
        pincode = findViewById(R.id.givepet_pincode);
        givepetapply = findViewById(R.id.givepetapply);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//            pettype.setOnItemSelectedListener(this);
//            ArrayAdapter ad
//                    = new ArrayAdapter(
//                    this,
//                    android.R.layout.simple_spinner_item,
//                    type);
//
//            ad.setDropDownViewResource(
//                    android.R.layout
//                            .simple_spinner_dropdown_item);
//
//            pettype.setAdapter(ad);

//
//            gender.setOnItemSelectedListener(this);
//            ArrayAdapter adapter
//                            android.R.layout.simple_spinner_item,
//                            gen);
//
//                adapter.setDropDownViewResource(
//                        android.R.layout
//                            .simple_spinner_dropdown_item);
//                gender.setAdapter(adapter);
//                    =new ArrayAdapter(
//                            this,

        pettype.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,type));
        pettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                petis = type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gender.setAdapter(new ArrayAdapter<String>(GivePetActivity.this,android.R.layout.simple_spinner_dropdown_item,gen));
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderis = gen[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        givepetapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // final String givepet_pettype = petis;
                final String givepet_breed = breed.getText().toString();
                final String givepet_age = age.getText().toString();
                final String givepet_weight = weight.getText().toString();
                //final String givepet_gender = gender.getText().toString();
                final String givepet_ownername = ownername.getText().toString();
                final String givepet_phone = phone.getText().toString();
                final String givepet_address = address.getText().toString();
                final String givepet_city = city.getText().toString();
                final String givepet_state = state.getText().toString();
                final String givepet_country = country.getText().toString();
                final String givepet_pincode = pincode.getText().toString();

                //condition to check if empty
                if ( petis.length()==0 || genderis.length() == 0 || givepet_breed.length() == 0 || givepet_age.length() == 0 || givepet_weight.length() == 0 || givepet_ownername.length() == 0 || givepet_phone.length() == 0 || givepet_address.length() == 0 || givepet_city.length() == 0 || givepet_state.length() == 0 || givepet_country.length() == 0 || givepet_pincode.length() == 0) {

                    Toast.makeText(getApplicationContext(), "All fields Are compulsory", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(givepet_age) >= 9) {
                    Toast.makeText(getApplicationContext(), "Sorry!!! Age must be 0 to 8 ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(givepet_weight) <= 25) {
                    Toast.makeText(getApplicationContext(), "Sorry!!! Weight must be greater than 25 kg", Toast.LENGTH_SHORT).show();
                    return;
                } else if(petis == "Select Type") {
                    Toast.makeText(getApplicationContext(), "Select Pet Type", Toast.LENGTH_SHORT).show();
                    return;
                }else if(genderis == "Select gender") {
                    Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                    return;
                }else if(givepet_phone.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                }else if(givepet_pincode.length() != 6) {
                    Toast.makeText(getApplicationContext(), "Pin Code must be 6 digits", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mdialog.show();
                    //create user with firebase
                    String id = mDatabase.push().getKey();
                    String uid = mAuth.getCurrentUser().getUid();

                    //sending data to user node
                    givepet_data data = new givepet_data(uid, petis, givepet_breed, givepet_age, givepet_weight, genderis, givepet_ownername, givepet_phone, givepet_address, givepet_city, givepet_state, givepet_country, givepet_pincode, id);
                    mDatabase.child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Applied Successfully", Toast.LENGTH_SHORT).show();
                            mdialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), AdoptActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("insertion error", "error " + e);
                            mdialog.dismiss();
                        }
                    });


                }


            }
        });
    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        petis = type[i];
//        genderis = gen[i];
//    }
//
//
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}





