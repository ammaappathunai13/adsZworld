package com.vjsm.sports.adszworld;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Random;

public class Account_Activation extends AppCompatActivity {

    private EditText referenceID;
    private FirebaseFirestore fb=FirebaseFirestore.getInstance();
    private TextView noReferenceId;
    private String ReferenceID,userId;
    private boolean isExsting;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    private SharedPreferences.Editor editor;
    private TextView help_video_for_Registration;
    private Button Activate;
    private  String result,Username,UserId;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__activation);
            help_video_for_Registration=(TextView)findViewById(R.id.register_And_signin);

            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences=getSharedPreferences("com.vjsm.sports.adszworld", Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
            userId=sharedPreferences.getString(getString(R.string.UserId),"");
            Username=sharedPreferences.getString(getString(R.string.LoginUserName),"");
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("please wait...");




            referenceID=(EditText)findViewById(R.id.reference_ID_EDittext);
        noReferenceId=(TextView)findViewById(R.id.no_reference_click_here);
        Activate=(Button)findViewById(R.id.activate_button);
            help_video_for_Registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ss=new Intent(getApplicationContext(),Help_Videos.class);

                    startActivity(ss);
                }
            });


            Activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String alphabet =
                        new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
                int n = alphabet.length(); //10

                  result = new String();
                Random r = new Random(); //11

                for (int i=0; i<10; i++) //
                {
                    result = result + alphabet.charAt(r.nextInt(n)); //13

                }

                progressDialog.show();
                ReferenceID=referenceID.getText().toString().trim();
                if(TextUtils.isEmpty(ReferenceID)){
                    referenceID.setError("Fill Reference Id");
                    progressDialog.cancel();
                }else{
                    progressDialog.show();
                    final CollectionReference clf = fb.collection("Reference_Details");
                    Query q1 = clf.whereEqualTo("ReferenceId", ReferenceID);
                    q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            isExsting=false;
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                String count =documentSnapshot.getString("Count") ;
                                String id=documentSnapshot.getString("ReferenceId");

                                if (id.equals(ReferenceID)){
                                    isExsting=true;
                                    int CountINc = Integer.parseInt(count) + 1;
                                    DocumentReference counin = fb.collection("Reference_Details").document(ReferenceID);
                                    counin.update("Count", String.valueOf(CountINc));

                                    String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
                                    int n = alphabet.length(); //10

                                    result = new String();
                                    final Random r = new Random(); //11

                                    for (int i = 0; i < 10; i++) //
                                    {
                                        result = result + alphabet.charAt(r.nextInt(n)); //13

                                    }

                                    DocumentReference documentReference = fb.collection("adsZworld_Account").document(userId);
                                    documentReference.update("AccountStatus", "true");
                                    documentReference.update("Balance", "0");
                                    documentReference.update("ReferenceCount",CountINc);
                                    documentReference.update("ReferenceId", result).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            HashMap<String, Object> ref = new HashMap<>();
                                            ref.put("ReferenceId", result);
                                            ref.put("UserId", userId);
                                            ref.put("Count", "0");
                                            fb.collection("Reference_Details").document(result).set(ref).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Account_Activation.this);
                                                    builder.setMessage("Account is Activated");
                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            progressDialog.cancel();
                                                            finish();
                                                        }
                                                    });
                                                    AlertDialog a = builder.create();
                                                    a.show();
                                                }
                                            });

                                            fb.collection("Reference_Details1").document(result).set(ref).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Account_Activation.this);
                                                    builder.setMessage("Account is Activated");
                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            progressDialog.cancel();
                                                            finish();
                                                        }
                                                    });
                                                    AlertDialog a = builder.create();
                                                    a.show();
                                                }
                                            });
                                        }
                                    });
                                }

                            }

                            if (!isExsting){
                                progressDialog.cancel();
                                referenceID.setError("Wrong Reference ID");
                                Toast.makeText(getApplicationContext(),"Wrong Reference If",Toast.LENGTH_LONG).show();
                            }
                        }
                    });






                }


            }
        });
        noReferenceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alphabet =
                        new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
                int n = alphabet.length(); //10

                result = new String();
                final Random r = new Random(); //11

                for (int i=0; i<10; i++) //
                {
                    result = result + alphabet.charAt(r.nextInt(n)); //13

                }
                progressDialog.show();
                DocumentReference documentReference=fb.collection("adsZworld_Account").document(userId);
                documentReference.update("AccountStatus","true");
                documentReference.update("Balance","0");
                documentReference.update("ReferenceId",result).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HashMap<String, Object> ref = new HashMap<>();
                        ref.put("ReferenceId", result);
                        ref.put("UserId", userId);
                        ref.put("Count","0");
                        fb.collection("Reference_Details").document(result).set(ref).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(Account_Activation.this);
                                builder.setMessage("Account is Activated");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog.cancel();
                                        finish();
                                    }
                                });
                                AlertDialog a=builder.create();
                                a.show();
                            }
                        });

                    }
                });
            }
        });

    }






}
