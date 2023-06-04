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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdoptionDetail extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ProgressDialog mdialog;
    String givepetID="";
    TextView givepet_pet_detail, givepet_pettype, givepet_breed, givepet_age, givepet_weight, givepet_gender,
             givepet_owner_detail, givepet_ownername, givepet_phone, givepet_address, givepet_city, givepet_state,
             givepet_country, givepet_pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_detail);

        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("Adoption Details");
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        drawerLayout=findViewById(R.id.drawer_layout);

        givepet_pet_detail = findViewById(R.id.givepet_detail_pet);
        givepet_owner_detail = findViewById(R.id.givepet_detail_owner);
        givepet_breed = findViewById(R.id.givepet_detail_breed);
        givepet_pettype = findViewById(R.id.givepet_detail_pettype);
        givepet_age = findViewById(R.id.givepet_detail_age);
        givepet_weight = findViewById(R.id.givepet_detail_weight);
        givepet_gender = findViewById(R.id.givepet_detail_gender);
        givepet_ownername = findViewById(R.id.givepet_detail_ownername);
        givepet_phone = findViewById(R.id.givepet_detail_phone);
        givepet_address = findViewById(R.id.givepet_detail_address);
        givepet_city = findViewById(R.id.givepet_detail_city);
        givepet_state = findViewById(R.id.givepet_detail_state);
        givepet_country = findViewById(R.id.givepet_detail_country);
        givepet_pincode = findViewById(R.id.givepet_detail_pincode);

        Intent intent = getIntent();
        givepetID = intent.getStringExtra("givepet_id");

        //  Toast.makeText(this, "did"+givepetID, Toast.LENGTH_SHORT).show();


        final DatabaseReference nm1= FirebaseDatabase.getInstance().getReference("givepet/"+givepetID);
        nm1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    
                    String ID = dataSnapshot.child("givepet_id").getValue().toString();
                    String breed = dataSnapshot.child("givepet_breed").getValue().toString();
                    String city = dataSnapshot.child("givepet_city").getValue().toString();
                    String age = dataSnapshot.child("givepet_age").getValue().toString();
                    String weight = dataSnapshot.child("givepet_weight").getValue().toString();
                    String pettype = dataSnapshot.child("givepet_pettype").getValue().toString();
                    String gender = dataSnapshot.child("givepet_gender").getValue().toString();
                    String ownername = dataSnapshot.child("givepet_ownername").getValue().toString();
                    String phone = dataSnapshot.child("givepet_phone").getValue().toString();
                    String address = dataSnapshot.child("givepet_address").getValue().toString();
                    String state = dataSnapshot.child("givepet_state").getValue().toString();
                    String country = dataSnapshot.child("givepet_country").getValue().toString();
                    String pincode = dataSnapshot.child("givepet_pincode").getValue().toString();

                    // Toast.makeText(DonationDetail.this, breed, Toast.LENGTH_SHORT).show();

                    givepet_pettype.setText("Pet Type : " +pettype);
                    givepet_breed.setText("Breed : " +breed);
                    givepet_age.setText("Age : " +age);
                    givepet_weight.setText("Weight : " +weight);
                    givepet_gender.setText("Gender : " +gender);
                    givepet_ownername.setText("Name : " +ownername);
                    givepet_phone.setText("Phone No. : " +phone);
                    givepet_address.setText("Address : " +address);
                    givepet_city.setText("City : " +city);
                    givepet_state.setText("State : " +state);
                    givepet_country.setText("Country : " +country);
                    givepet_pincode.setText("Pincode : " +pincode);

                }


            }


            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);

    }
    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
    }

    public void clickDonate(View view){
        MainActivity.redirectActivity(this,DonateActivity.class);
    }

    public void clickAdopt(View view){
        MainActivity.redirectActivity(this,AdoptActivity.class);
    }

    public void clickPetfood(View view){
        MainActivity.redirectActivity(this,PetfoodActivity.class);
    }

    public void clickFAQs(View view){
        MainActivity.redirectActivity(this,FAQsActivity.class);
    }

    public void clickChat(View view){
        MainActivity.redirectActivity(this,Chat.class);
    }

    public void clickRegister(View view){
        MainActivity.redirectActivity(this,RegistrationActivity.class);
    }

    public void clickLogout(View view){

        logout(this);

    }

    public void logout(Activity activity) {
        //initialize
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        //set message
        builder.setMessage("Are you sure you want to logout?");
        //positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
                mAuth.signOut();
                activity.finishAffinity();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));            }
        });
        //negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }





    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

}