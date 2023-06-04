package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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

import com.example.petapp1.models.donate_data;
import com.example.petapp1.models.adopt_data;
import com.example.petapp1.models.givepet_data;
import com.example.petapp1.models.adoption_data;
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

public class DonateActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ProgressDialog mdialog;
    DatabaseReference mDatabase;
    TextView Logout_btn;
    ImageView Logout_ic;

    Button donor_btn, donatesearch;
    Spinner pettype, bloodgroup;
    String petis ="";
    String[] type = { "Select Type", "Cat", "Dog" };
    String bloodis ="";
    String[] dogblood = {"Select Blood group", "DEA 1.1", "DEA 1.2", "DEA 3", "DEA 4", "DEA 5", "DEA 7"};
    String[] catblood ={"Select Blood group", "A", "B", "AB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        mDatabase = FirebaseDatabase.getInstance().getReference("/donate");

        mdialog = new ProgressDialog(DonateActivity.this);
        mdialog.setTitle("Register");
        mdialog.setMessage("Registering Please wait");

        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout);
        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("Donate");

        donor_btn = findViewById(R.id.Button_1);
        pettype = findViewById(R.id.donate_pettype);
        bloodgroup = findViewById(R.id.donate_bloodgroup);
        donatesearch = findViewById(R.id.donatesearch);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        pettype.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,type));
        pettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                petis = type[i];
                if (petis == "Dog") {
                    bloodgroup.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dogblood));
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
                    bloodgroup.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, catblood));
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


//        currentUser.getUid();
        donor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DonorActivity.class);
                startActivity(intent);
            }
        });

        donatesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check conditions
                if (petis == "Select Type"){
                    Toast.makeText(getApplicationContext(), "Select Pet Type", Toast.LENGTH_SHORT).show();
                    return;
                }else if(bloodis == "Select Blood group") {
                    Toast.makeText(getApplicationContext(), "Select a Blood Group", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    Intent i = new Intent(getApplicationContext(), TakerActivity.class);
                    i.putExtra("pettype", petis);
                    i.putExtra("blood", bloodis);
                    startActivity(i);
                }
            }
        });
    }

    public void ClickMenu(View view){

        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);

    }

    public void ClickLogo(View view){

        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        redirectActivity(this,MainActivity.class);
    }

    public void clickDonate(View view){
        recreate();
    }

    public void clickAdopt(View view){
        redirectActivity(this,AdoptActivity.class);
    }

    public void clickPetfood(View view){
        redirectActivity(this,PetfoodActivity.class);
    }

    public void clickFAQs(View view){
        redirectActivity(this,FAQsActivity.class);
    }

    public void clickChat(View view){
        redirectActivity(this,Chat.class);
    }

    public void clickRegister(View view){
        redirectActivity(this,RegistrationActivity.class);
    }


    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

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
        closeDrawer(drawerLayout);
    }
}