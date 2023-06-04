package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petapp1.adapter.addpetfood_list;
import com.example.petapp1.adapter.donate_list;
import com.example.petapp1.models.addpetfood_data;
import com.example.petapp1.models.donor_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PetfoodActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    TextView Logout_btn;
    ImageView Logout_ic;
    Button addnew;
    String pet_type = "";
    private List<addpetfood_data> addpetfoodData;
    private RecyclerView rv;
    private addpetfood_list adapter;
    SearchView searchView;
    DatabaseReference mDatabase1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petfood);

        Intent intent = getIntent();

        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase1 = FirebaseDatabase.getInstance().getReference("/admin/"+uid);

        drawerLayout=findViewById(R.id.drawer_layout);
        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("Pet Food");
        pet_type = intent.getStringExtra("pettype");
        searchView = findViewById(R.id.searchView);
        addnew = findViewById(R.id.addnew);
        addnew.setVisibility(View.GONE);
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

        rv=(RecyclerView)findViewById(R.id.addfood_recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        addpetfoodData=new ArrayList<>();
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("addpetfood");
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    for(DataSnapshot id:dataSnapshot.getChildren()){
                        String ID = id.child("add_id").getValue().toString();
                        String pettype = id.child("add_pettype").getValue().toString();
                        String foodname = id.child("add_foodname").getValue().toString();

                        addpetfood_data work = new addpetfood_data("", pettype, foodname, "", "", "",ID);
                        addpetfoodData.add(work);
                        adapter = new addpetfood_list(addpetfoodData);
                        rv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Some error", Toast.LENGTH_SHORT).show();
            }
        });
      //  Toast.makeText(this,mDatabase1.child, Toast.LENGTH_SHORT).show();



        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if(snapshot.exists()){
                   // Toast.makeText(PetfoodActivity.this, "admin", Toast.LENGTH_SHORT).show();
                    addnew.setVisibility(View.VISIBLE);
                }else{
                 //   Toast.makeText(PetfoodActivity.this, "not admin", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });





        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPetfood.class);
                startActivity(intent);
            }
        });

        //        currentUser.getUid();
        if(currentUser==null){
            Logout_btn.setVisibility(View.GONE);
            Logout_ic.setVisibility(View.GONE);

        }
    }

    private void search(String str) {
        List<addpetfood_data> myList = new ArrayList<>();
        for (addpetfood_data object :  addpetfoodData)
        {
            if(object.getAdd_pettype().toLowerCase().contains(str.toLowerCase()) || object.getAdd_foodname().toLowerCase().contains(str.toLowerCase()) )
            {
                myList.add(object);

            }
        }
        addpetfood_list adapterclass = new addpetfood_list(myList);
        rv.setAdapter(adapterclass);
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
        recreate();
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