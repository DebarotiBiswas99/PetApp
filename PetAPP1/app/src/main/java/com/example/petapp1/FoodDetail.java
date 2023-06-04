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

public class FoodDetail extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ProgressDialog mdialog;
    String addpetfoodID="";
    TextView addpetfood_petfood_detail, addpetfood_pettype, addpetfood_foodname, addpetfood_foodquantity, addpetfood_foodtype, addpetfood_suitablefor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("Pet Food Details");
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        drawerLayout=findViewById(R.id.drawer_layout);

        addpetfood_petfood_detail = findViewById(R.id.food_detail_petfood);
        addpetfood_pettype = findViewById(R.id.food_detail_pettype);
        addpetfood_foodname = findViewById(R.id.food_detail_foodname);
        addpetfood_foodquantity = findViewById(R.id.food_detail_foodquantity);
        addpetfood_foodtype = findViewById(R.id.food_detail_foodtype);
        addpetfood_suitablefor = findViewById(R.id.food_detail_suitablefor);

        Intent intent = getIntent();
        addpetfoodID = intent.getStringExtra("addpetfood_id");
        //  Toast.makeText(this, "did"+addpetfoodID, Toast.LENGTH_SHORT).show();

        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("addpetfood/"+addpetfoodID);
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    String ID = dataSnapshot.child("add_id").getValue().toString();
                    String pettype = dataSnapshot.child("add_pettype").getValue().toString();
                    String foodname = dataSnapshot.child("add_foodname").getValue().toString();
                    String foodquantity = dataSnapshot.child("add_foodquantiy").getValue().toString();
                    String foodtype = dataSnapshot.child("add_foodtype").getValue().toString();
                    String suitablefor = dataSnapshot.child("add_suitablefor").getValue().toString();

                    // Toast.makeText(foodDetail.this, breed, Toast.LENGTH_SHORT).show();

                    addpetfood_pettype.setText("Pet Type : " +pettype);
                    addpetfood_foodname.setText("Food Name : " +foodname);
                    addpetfood_foodquantity.setText("Food Quantity : " +foodquantity +" "+"kg");
                    addpetfood_foodtype.setText("Food Type : " +foodtype);
                    //Toast.makeText(FoodDetail.this, suitablefor.toString(), Toast.LENGTH_SHORT).show();
                    addpetfood_suitablefor.setText("Suitable For : " +suitablefor);

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