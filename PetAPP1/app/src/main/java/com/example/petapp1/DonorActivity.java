package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.example.petapp1.models.donor_data;
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

public class DonorActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    Button donorapply;
    EditText breed, age, weight, ownername, phone, address, city, state, country, pincode;
    ImageView backbtn;
    Spinner pettype, gender, bloodgroup;
    FirebaseAuth mAuth;
    ProgressDialog mdialog;
    DatabaseReference mDatabase;
    String petis ="";
    String[] type = { "Select Type", "Cat", "Dog" };
    String genderis ="";
    String[] gen = {"Select Gender", "Male", "Female"};
    String bloodis ="";
    String[] dogblood = {"Select blood group", "DEA 1.1", "DEA 1.2", "DEA 3", "DEA 4", "DEA 5", "DEA 7"};
    String[] catblood ={"Select blood group", "A", "B", "AB"};

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);


        mDatabase = FirebaseDatabase.getInstance().getReference("/donor");

        mdialog = new ProgressDialog(DonorActivity.this);
        mdialog.setTitle("Register");
        mdialog.setMessage("Registering Please wait");

        pettype = findViewById(R.id.donor_pettype);
        breed = findViewById(R.id.donor_breed);
        age = findViewById(R.id.donor_age);
        weight = findViewById(R.id.donor_weight);
        gender = findViewById(R.id.donor_gender);
        bloodgroup = findViewById(R.id.donor_bloodgroup);
        ownername = findViewById(R.id.donor_ownername);
        phone = findViewById(R.id.donor_phone);
        address = findViewById(R.id.donor_address);
        city = findViewById(R.id.donor_city);
        state = findViewById(R.id.donor_state);
        country = findViewById(R.id.donor_country);
        pincode = findViewById(R.id.donor_pincode);
        donorapply = findViewById(R.id.donorapply);


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
                if (petis == "Dog") {
                    bloodgroup.setAdapter(new ArrayAdapter<String>(DonorActivity.this, android.R.layout.simple_spinner_dropdown_item, dogblood));
                    bloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            bloodis = dogblood[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                else if (petis == "Cat") {
                    bloodgroup.setAdapter(new ArrayAdapter<String>(DonorActivity.this, android.R.layout.simple_spinner_dropdown_item, catblood));
                    bloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            bloodis = catblood[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gender.setAdapter(new ArrayAdapter<String>(DonorActivity.this,android.R.layout.simple_spinner_dropdown_item,gen));
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderis = gen[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        donorapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // final String donor_pettype = petis;
                final String donor_breed = breed.getText().toString();
                final String donor_age = age.getText().toString();
                final String donor_weight = weight.getText().toString();
                //final String donor_gender = gender.getText().toString();
                // final String donor_bloodgroup = bloodgroup.getText().toString();
                final String donor_ownername = ownername.getText().toString();
                final String donor_phone = phone.getText().toString();
                final String donor_address = address.getText().toString();
                final String donor_city = city.getText().toString();
                final String donor_state = state.getText().toString();
                final String donor_country = country.getText().toString();
                final String donor_pincode = pincode.getText().toString();

                //condition to check if empty
                if ( petis.length()==0 || genderis.length() == 0 || donor_breed.length() == 0 || donor_age.length() == 0 || donor_weight.length() == 0 || bloodis.length() == 0 || donor_ownername.length() == 0 || donor_phone.length() == 0 || donor_address.length() == 0 || donor_city.length() == 0 || donor_state.length() == 0 || donor_country.length() == 0 || donor_pincode.length() == 0) {

                    Toast.makeText(getApplicationContext(), "All fields are compulsory", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(donor_age) >= 9) {
                    Toast.makeText(getApplicationContext(), "Sorry!!! Age must be 0 to 8 ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(donor_weight) <= 25) {
                    Toast.makeText(getApplicationContext(), "Sorry!!! Weight must be greater than 25 kg", Toast.LENGTH_SHORT).show();
                    return;
                } else if(petis == "Select Type") {
                    Toast.makeText(getApplicationContext(), "Select Pet Type", Toast.LENGTH_SHORT).show();
                    return;
                }else if(genderis == "Select gender") {
                    Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                    return;
                }else if(bloodis == "Select blood group") {
                    Toast.makeText(getApplicationContext(), "Select a Blood Group", Toast.LENGTH_SHORT).show();
                    return;
                }else if(donor_phone.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                }else if(donor_pincode.length() != 6) {
                    Toast.makeText(getApplicationContext(), "Pin Code must be 6 digits", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mdialog.show();
                    //create user with firebase
                    String id = mDatabase.push().getKey();
                    String uid = mAuth.getCurrentUser().getUid();

                    //sending data to user node
                    donor_data data = new donor_data(uid, petis, donor_breed, donor_age, donor_weight, genderis, bloodis, donor_ownername, donor_phone, donor_address, donor_city, donor_state, donor_country, donor_pincode,id);
                    mDatabase.child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Applied Successfully", Toast.LENGTH_SHORT).show();
                            mdialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), DonateActivity.class));
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





