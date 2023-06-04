package com.example.petapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petapp1.adapter.chatAdapter;
import com.example.petapp1.models.chat_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chat extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView pageTitle;
    FirebaseUser currentUser;
    EditText txt_msg;
    ImageButton send;
    private FirebaseAuth mAuth;
    private List<chat_model> chat_data;
    private chatAdapter adapter;
    RecyclerView rv;
    String hisUid;
    String myUid;

    boolean notify = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        pageTitle = findViewById(R.id.pagename);
        pageTitle.setText("Chat");
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout);
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            myUid = currentUser.getUid();
        }else{
            mAuth.signOut();
        }

        final DateFormat df = new SimpleDateFormat("h:mm a");


        txt_msg = findViewById(R.id.type_chat);
        send = findViewById( R.id.send_chat);
        rv = findViewById(R.id.chat_recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        //  apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);
        chat_data=new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            hisUid  = bundle.getString("hisUid");


        }


        //for Loading messages

        //firebase

        final DatabaseReference nm1= FirebaseDatabase.getInstance().getReference("/chat");

        nm1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    chat_data.clear();
                    float total = 0;
                    for(DataSnapshot id:dataSnapshot.getChildren()){

                        id.getValue();
                        chat_model rd = id.getValue(chat_model.class);



                        chat_data.add(rd);
                        adapter=new chatAdapter(chat_data);
                        rv.scrollToPosition(adapter.getItemCount() - 1);
                        rv.setAdapter(adapter);

                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt_msg.getText().toString().isEmpty()) {
                    notify = true;
                    final DatabaseReference mdb = FirebaseDatabase.getInstance().getReference("/chat");
                    String id = mdb.push().getKey();

                    chat_model data = new chat_model(currentUser.getDisplayName(), currentUser.getUid(), id, df.format(Calendar.getInstance().getTime()).toString(), "", txt_msg.getText().toString());
                    mdb.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                            txt_msg.setText("");



//
//                            final DatabaseReference mdb2 = FirebaseDatabase.getInstance().getReference("adminChat/" + currentUser.getUid()+"/read");
//
//                            mdb2.setValue("1");
//                            final DatabaseReference mdb3 = FirebaseDatabase.getInstance().getReference("adminChat/" + currentUser.getUid()+"/userr_id");
//
//                            mdb3.setValue(currentUser.getUid());
//
//                            final DatabaseReference mdb4 = FirebaseDatabase.getInstance().getReference("adminChat/" + currentUser.getUid()+"/userr_name");
//
//                            mdb4.setValue(currentUser.getDisplayName());
//
//                            if(notify)
//                            {
//                                //senNotification(currentUser.getUid(),currentUser.getDisplayName(),"You have a new message");
//                            }
//                            notify = false;



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            Toast.makeText(getApplicationContext(), "problem" + e, Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {

                    Toast.makeText(getApplicationContext(), "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

//    private void senNotification(String uid, String displayName, String you_have_a_new_message) {
//        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
//        Query  query  = allTokens.orderByKey().equalTo(hisUid);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds: snapshot.getChildren())
//                {
//                    Token token = ds.getValue(Token.class);
//                    Data data  = new Data(myUid,"name :message","new Message","Z5XMcAUevsezPbvExnU4cDI3IyX2",R.drawable.ic_action_name);
//                    Sender sender = new Sender( data,token.getToken());
//                    apiService.sendNotification(sender).enqueue(new Callback<Response>() {
//                        @Override
//                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                            Toast.makeText(getApplicationContext(),""+response.message(),Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Response> call, Throwable t) {
//
//                        }
//                    });
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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

    public void clickChat(View view){ recreate(); }

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