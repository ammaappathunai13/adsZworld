package com.vjsm.sports.adszworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pusher.pushnotifications.PushNotifications;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private Button signin,signup;
    private EditText userid,password;
    private TextView help_video_for_Registration;
    private String UserId,Password;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private FirebaseFirestore fb=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;
    private Boolean isExisting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initilization();
        //shared preference
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences=getSharedPreferences("com.vjsm.sports.adszworld", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if((!sharedPreferences.getString(getString(R.string.UserId),"").equals(""))) {
            if (!(sharedPreferences.getString(getString(R.string.Password), "")).equals("")) {
                Intent s = new Intent(MainActivity.this, HomePage.class);
                String userId = getString(R.string.UserId);
                startActivity(s);
                this.finish();
            }

        }
        else{
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.cancel();
                    getvalues();
                    check_and_Login();

                }
            });
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("please wait...");
        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Intent registeration=new Intent(MainActivity.this,Registration_Activity.class);
                startActivity(registeration);
                progressDialog.cancel();
            }
        });

        help_video_for_Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ss=new Intent(getApplicationContext(),Help_Videos.class);

                startActivity(ss);
            }
        });




    }

    private void check_and_Login() {

        if (TextUtils.isEmpty(UserId) && TextUtils.isEmpty(Password)) {
            userid.setError("Fill UserId");
            password.setError("Fill Password");

            Toast.makeText(getApplicationContext(),"Fill Username and password",Toast.LENGTH_LONG).show();
            progressDialog.cancel();
        } else if (TextUtils.isEmpty(UserId)) {
            userid.setError("Fill UserId");
            progressDialog.cancel();
        } else if (TextUtils.isEmpty(Password)) {
            password.setError("Fill Password");
            progressDialog.cancel();
        }
        else{
            progressDialog.show();

            collectionReference=fb.collection("adsZworld_Auth");
            Query q1=collectionReference.whereEqualTo("UserId",UserId);
            final DocumentReference reference=fb.collection("adsZworld_Auth").document(UserId);

            q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    isExisting=false;
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        final String Uid=documentSnapshot.getString("UserId");
                        if (Uid.equals(UserId)){
                            isExisting=true;
                            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        String pass=    document.getString("Password");
                                        String Name=document.getString("UserName");
                                        String Number=document.getString("PhoneNumber");
                                        if (document.exists()){
                                            if(pass.equals(Password)){

                                                editor.putString(getString(R.string.UserId),UserId);
                                                editor.commit();

                                                editor.commit();
                                                editor.putString(getString(R.string.Password),Password);
                                                editor.commit();
                                                editor.putString(getString(R.string.LoginUserName),Name);
                                                editor.commit();
                                                editor.putString(getString(R.string.LoginUser_PhoneNumber),Number);
                                                editor.commit();
                                                isExisting=true;
                                                Intent success=new Intent(MainActivity.this,HomePage.class);
                                                startActivity(success);
                                                progressDialog.cancel();
                                                finish();
                                            }
                                            else{
                                                progressDialog.cancel();
                                                password.setError("Invalid Password");
                                                Toast.makeText(getApplicationContext(),"Please Fill Correct Password",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else{
                                            progressDialog.cancel();
                                            Toast.makeText(getApplicationContext(),"No such a Details",Toast.LENGTH_LONG).show();
                                        }
                                    }else
                                    {
                                        Toast.makeText(getApplicationContext(),"Database Retrive Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                    if(!isExisting){
                        {
                            progressDialog.cancel();
                            userid.setError("Invalid UserId");
                            Toast.makeText(getApplicationContext(),"Wrong UserId,Fill Correct UserId or new User Signup",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });


        }
    }

    private void getvalues() {
        UserId=userid.getText().toString().trim();
        Password=password.getText().toString().trim();

    }

    private void initilization() {

        signin=(Button)findViewById(R.id.signin);
        signup=(Button)findViewById(R.id.signup);
        userid=(EditText)findViewById(R.id.userid);
        password=(EditText)findViewById(R.id.password);
        help_video_for_Registration=(TextView)findViewById(R.id.register_And_signin);

    }
}
