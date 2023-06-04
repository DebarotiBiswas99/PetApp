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

import com.example.petapp1.models.addpetfood_data;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPetfood extends AppCompatActivity {

    FirebaseUser currentUser;
    Button addfood;
    EditText foodname, foodquantity;
    ImageView backbtn;
    Spinner pettype, foodtype, suitablefor;
    FirebaseAuth mAuth;
    ProgressDialog mdialog;
    DatabaseReference mDatabase,mDatabase1;
    String petis ="";
    String[] type = { "Select Type", "Cat", "Dog" };
    String foodis ="";
    String[] ftype = { "Select Type", "Dry", "Moist"};
    String suitableis ="";
    String [] sutype = { "Select Type", "New Born", "Adult", "Any Age Group"};

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_petfood);

        mDatabase = FirebaseDatabase.getInstance().getReference("/addpetfood");
        mDatabase1 = FirebaseDatabase.getInstance().getReference("/admin");

        mdialog = new ProgressDialog(AddPetfood.this);
        mdialog.setTitle("Register");
        mdialog.setMessage("Registering Please wait");

        pettype = findViewById(R.id.add_pettype);
        foodname = findViewById(R.id.add_foodname);
        foodquantity = findViewById(R.id.add_foodquantity);
        foodtype = findViewById(R.id.add_foodtype);
        suitablefor = findViewById(R.id.add_suitablefor);
        addfood = findViewById(R.id.addfood);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
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
       // Toast.makeText(this, mDatabase1.child(uid).getKey(), Toast.LENGTH_SHORT).show();

//        mDatabase1.addValueEventListener (new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                }else{
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled( DatabaseError error) {
//
//            }
//        });


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

        foodtype.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,ftype));
        foodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foodis = ftype[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        suitablefor.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,sutype));
        suitablefor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                suitableis = sutype[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // final String donor_pettype = petis;
                final String add_foodname = foodname.getText().toString();
                final String add_foodquantity = foodquantity.getText().toString();

                //condition to check if empty
                if ( petis.length()==0 || add_foodname.length() == 0 || add_foodquantity.length() == 0 || foodis.length()==0 || suitableis.length()==0 ) {
                    Toast.makeText(getApplicationContext(), "All fields are compulsory", Toast.LENGTH_SHORT).show();
                }else if(petis == "Select Type") {
                    Toast.makeText(getApplicationContext(), "Select Pet Type", Toast.LENGTH_SHORT).show();
                    return;
                }else if(foodis == "Select Type") {
                    Toast.makeText(getApplicationContext(), "Select Food Type", Toast.LENGTH_SHORT).show();
                    return;
                }else if(suitableis == "Select Type") {
                    Toast.makeText(getApplicationContext(), "Select Suitable For", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mdialog.show();
                    //create user with firebase
                    String id = mDatabase.push().getKey();


                    //sending data to user node
                    addpetfood_data data = new addpetfood_data(uid, petis, add_foodname, add_foodquantity, foodis, suitableis, id);
                    mDatabase.child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Applied Successfully", Toast.LENGTH_SHORT).show();
                            mdialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), PetfoodActivity.class));
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





