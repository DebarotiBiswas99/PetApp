package com.example.petapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petapp1.adapter.adopt_list;
import com.example.petapp1.adapter.donate_list;
import com.example.petapp1.models.donor_data;
import com.example.petapp1.models.givepet_data;
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

public class AdoptionActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    String pet_type = "";
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private List<givepet_data> givepetData;
    private RecyclerView rv1;
    private adopt_list adapter1;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption);

        Intent intent = getIntent();

        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("List of Pets");
        pet_type = intent.getStringExtra("pettype");
         Toast.makeText(getApplicationContext(),"pet type is "+pet_type,Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        drawerLayout=findViewById(R.id.drawer_layout);
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

        rv1=(RecyclerView)findViewById(R.id.givepet_recycler);
        rv1.setHasFixedSize(true);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        givepetData=new ArrayList<>();
        final DatabaseReference nm1= FirebaseDatabase.getInstance().getReference("givepet");
        nm1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    for(DataSnapshot id:dataSnapshot.getChildren()){
                        String ID = id.child("givepet_id").getValue().toString();
                        String breed=id.child("givepet_breed").getValue().toString();
                        String city =id.child("givepet_city").getValue().toString();
                        String state =id.child("givepet_state").getValue().toString();
                        String pettype =id.child("givepet_pettype").getValue().toString();

                        if(pettype.equals(pet_type)) {

                            givepet_data work = new givepet_data("", pettype, breed, "", "", "", "", "", "", city, state, "", "",ID);
                            givepetData.add(work);
                            adapter1 = new adopt_list(givepetData);
                            rv1.setAdapter(adapter1);
                        }
                        else{

                            //Toast.makeText(AdoptionActivity.this, "v pettype"+pettype, Toast.LENGTH_SHORT).show();
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
        List<givepet_data> myList = new ArrayList<>();
        for (givepet_data object :  givepetData)
        {
            if(object.getGivepet_state().toLowerCase().contains(str.toLowerCase()) || object.getGivepet_city().toLowerCase().contains(str.toLowerCase()) || object.getGivepet_breed().toLowerCase().contains(str.toLowerCase()) )
            {
                myList.add(object);

            }
        }
        adopt_list adapterclass = new adopt_list(myList);
        rv1.setAdapter(adapterclass);
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

    public void clickRegister(View view){
        MainActivity.redirectActivity(this,RegistrationActivity.class);
    }

    public void clickLogout(View view){

        logout(this);

    }

    private static void logout(Activity activity) {
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
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
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
