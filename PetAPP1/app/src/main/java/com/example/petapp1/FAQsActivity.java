package com.example.petapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FAQsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    TextView Logout_btn;
    ImageView Logout_ic;
    ImageView arrow1,arrow2,arrow3,arrow4,arrow5,arrow6;
    CardView q1,q1a,q2,q2a,q3,q3a,q4,q4a,q5,q5a,q6,q6a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        drawerLayout=findViewById(R.id.drawer_layout);
        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("FAQs");
        arrow1 = findViewById(R.id.arrow1);
        arrow2 = findViewById(R.id.arrow2);
        arrow3 = findViewById(R.id.arrow3);
        arrow4 = findViewById(R.id.arrow4);
        arrow5 = findViewById(R.id.arrow5);
        arrow6 = findViewById(R.id.arrow6);
        q1 = findViewById(R.id.q1);
        q1a = findViewById(R.id.q1a);
        q2 = findViewById(R.id.q2);
        q2a = findViewById(R.id.q2a);
        q3 = findViewById(R.id.q3);
        q3a = findViewById(R.id.q3a);
        q4 = findViewById(R.id.q4);
        q4a = findViewById(R.id.q4a);
        q5 = findViewById(R.id.q5);
        q5a = findViewById(R.id.q5a);
        q6 = findViewById(R.id.q6);
        q6a = findViewById(R.id.q6a);


//        currentUser.getUid();

        arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q1a.getVisibility() == View.GONE)
                {
                    arrow1.setRotation(180);
                    q1a.setVisibility(View.VISIBLE);
                }else{
                    arrow1.setRotation(360);
                    q1a.setVisibility(View.GONE);
                }
            }
        });
        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q2a.getVisibility() == View.GONE)
                {
                    arrow2.setRotation(180);
                    q2a.setVisibility(View.VISIBLE);
                }else{
                    arrow2.setRotation(360);
                    q2a.setVisibility(View.GONE);
                }
            }
        });
        arrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q3a.getVisibility() == View.GONE)
                {
                    arrow3.setRotation(180);
                    q3a.setVisibility(View.VISIBLE);
                }else{
                    arrow3.setRotation(360);
                    q3a.setVisibility(View.GONE);
                }
            }
        });
        arrow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q4a.getVisibility() == View.GONE)
                {
                    arrow4.setRotation(180);
                    q4a.setVisibility(View.VISIBLE);
                }else{
                    arrow4.setRotation(360);
                    q4a.setVisibility(View.GONE);
                }
            }
        });

        arrow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q5a.getVisibility() == View.GONE)
                {
                    arrow5.setRotation(180);
                    q5a.setVisibility(View.VISIBLE);
                }else{
                    arrow5.setRotation(360);
                    q5a.setVisibility(View.GONE);
                }
            }
        });

        arrow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q6a.getVisibility() == View.GONE)
                {
                    arrow6.setRotation(180);
                    q6a.setVisibility(View.VISIBLE);
                }else{
                    arrow6.setRotation(360);
                    q6a.setVisibility(View.GONE);
                }
            }
        });

        if(currentUser==null){
            Logout_btn.setVisibility(View.GONE);
            Logout_ic.setVisibility(View.GONE);

        }
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
        recreate();
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

    public void clickLogin(View view){

        login();

    }

    public  void login(){

        if(currentUser != null)
        {
            Toast.makeText(getApplicationContext(),"You are already logged in",Toast.LENGTH_SHORT).show();
        }else {

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

}