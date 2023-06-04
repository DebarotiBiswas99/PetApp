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

public class DonationDetail extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ProgressDialog mdialog;
    String donorID="";
    TextView donor_pet_detail, donor_pettype, donor_breed, donor_age, donor_weight, donor_gender, donor_bloodgroup,
             donor_owner_detail, donor_ownername, donor_phone, donor_address, donor_city, donor_state,
             donor_country, donor_pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);

        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("Donor Details");
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        drawerLayout=findViewById(R.id.drawer_layout);

        donor_pet_detail = findViewById(R.id.donor_detail_pet);
        donor_owner_detail = findViewById(R.id.donor_detail_owner);
        donor_breed = findViewById(R.id.donor_detail_breed);
        donor_pettype = findViewById(R.id.donor_detail_pettype);
        donor_age = findViewById(R.id.donor_detail_age);
        donor_weight = findViewById(R.id.donor_detail_weight);
        donor_gender = findViewById(R.id.donor_detail_gender);
        donor_bloodgroup = findViewById(R.id.donor_detail_bloodgroup);
        donor_ownername = findViewById(R.id.donor_detail_ownername);
        donor_phone = findViewById(R.id.donor_detail_phone);
        donor_address = findViewById(R.id.donor_detail_address);
        donor_city = findViewById(R.id.donor_detail_city);
        donor_state = findViewById(R.id.donor_detail_state);
        donor_country = findViewById(R.id.donor_detail_country);
        donor_pincode = findViewById(R.id.donor_detail_pincode);

        Intent intent = getIntent();
        donorID = intent.getStringExtra("donor_id");
        //  Toast.makeText(this, "did"+donorID, Toast.LENGTH_SHORT).show();

        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("donor/"+donorID);
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    String ID = dataSnapshot.child("donor_id").getValue().toString();
                    String breed = dataSnapshot.child("donor_breed").getValue().toString();
                    String city = dataSnapshot.child("donor_city").getValue().toString();
                    String blood = dataSnapshot.child("donor_bloodgroup").getValue().toString();
                    String age = dataSnapshot.child("donor_age").getValue().toString();
                    String weight = dataSnapshot.child("donor_weight").getValue().toString();
                    String pettype = dataSnapshot.child("donor_pettype").getValue().toString();
                    String gender = dataSnapshot.child("donor_gender").getValue().toString();
                    String ownername = dataSnapshot.child("donor_ownername").getValue().toString();
                    String phone = dataSnapshot.child("donor_phone").getValue().toString();
                    String address = dataSnapshot.child("donor_address").getValue().toString();
                    String state = dataSnapshot.child("donor_state").getValue().toString();
                    String country = dataSnapshot.child("donor_country").getValue().toString();
                    String pincode = dataSnapshot.child("donor_pincode").getValue().toString();

                    // Toast.makeText(DonationDetail.this, breed, Toast.LENGTH_SHORT).show();

                    donor_pettype.setText("Pet Type : " +pettype);
                    donor_breed.setText("Breed : " +breed);
                    donor_age.setText("Age : " +age);
                    donor_weight.setText("Weight : " +weight);
                    donor_gender.setText("Gender : " +gender);
                    donor_bloodgroup.setText("Bloodgroup : " +blood);
                    donor_ownername.setText("Name : " +ownername);
                    donor_phone.setText("Phone No. : " +phone);
                    donor_address.setText("Address : " +address);
                    donor_city.setText("City : " +city);
                    donor_state.setText("State : " +state);
                    donor_country.setText("Country : " +country);
                    donor_pincode.setText("Pincode : " +pincode);


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