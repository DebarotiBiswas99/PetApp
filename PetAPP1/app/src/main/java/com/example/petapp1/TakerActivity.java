package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petapp1.adapter.donate_list;
import com.example.petapp1.models.donor_data;
import com.example.petapp1.models.taker_data;
import com.example.petapp1.models.taker_data;
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

import java.util.ArrayList;
import java.util.List;

public class TakerActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String pet_type = "";
    String blood_group = "";
    private List<donor_data> donorData;
    private RecyclerView rv;
    private donate_list adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taker);

            Intent intent = getIntent();

            pageTitle = findViewById(R.id.pagename);
            pageTitle.setText("List of Donors");
            pet_type = intent.getStringExtra("pettype");
            blood_group = intent.getStringExtra("blood");
            mAuth = FirebaseAuth.getInstance();
            currentUser= mAuth.getCurrentUser();
            drawerLayout = findViewById(R.id.drawer_layout);
            searchView = findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
       // Toast.makeText(getApplicationContext(),"pet type is"+pet_type,Toast.LENGTH_SHORT).show();

       // Toast.makeText(getApplicationContext(),"blood group is"+blood_group,Toast.LENGTH_SHORT).show();
        rv=(RecyclerView)findViewById(R.id.donor_recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        donorData=new ArrayList<>();
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("donor");
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    for(DataSnapshot id:dataSnapshot.getChildren()){
                        String ID = id.child("donor_id").getValue().toString();
                        String breed=id.child("donor_breed").getValue().toString();
                        String city =id.child("donor_city").getValue().toString();
                        String state =id.child("donor_state").getValue().toString();
                        String blood =id.child("donor_bloodgroup").getValue().toString();
                        String pettype =id.child("donor_pettype").getValue().toString();

                        if(pettype.equals(pet_type) && blood.equals(blood_group)) {

                            donor_data work = new donor_data("", pettype, breed, "", "", "", blood, "", "", "", city, state, "", "",ID);
                            donorData.add(work);
                            adapter = new donate_list(donorData);
                            rv.setAdapter(adapter);
                        }
                        else{

                            //Toast.makeText(TakerActivity.this, "v pettype"+pettype, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Some error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void search(String str) {
        List<donor_data> myList = new ArrayList<>();
        for (donor_data object :  donorData)
        {
            if(object.getDonor_state().toLowerCase().contains(str.toLowerCase()) || object.getDonor_city().toLowerCase().contains(str.toLowerCase()) || object.getDonor_breed().toLowerCase().contains(str.toLowerCase()) )
            {
                myList.add(object);

            }
        }
        donate_list adapterclass = new donate_list(myList);
        rv.setAdapter(adapterclass);
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
        redirectActivity(this,DonateActivity.class);
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
