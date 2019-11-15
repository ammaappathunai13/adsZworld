package com.vjsm.sports.adszworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CodeVerification extends AppCompatActivity {
    private  String VerificationId;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private FirebaseAuth mauth;
    private EditText codes;
    private SharedPreferences sharedPreferences;
    private EditText Username,Password;
    private SharedPreferences.Editor editor;
    private String date;
    private Double totalamount=0.0;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference reference=firebaseDatabase.getReference();
    private Button b1;
    private String phone,empty,usename,usepass,useid;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences=getSharedPreferences("com.vjsm.sports.adszworld", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        initialization();
         date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        SendVerificationCode(phone);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code= codes.getText().toString();
                if(code.equals("")||code.length()<6){
                    codes.setError("Check Code");
                    codes.requestFocus();
                    return;
                }
                codes.setText(code);
                CodeCkeck(code);
                progressDialog.show();
            }
        });

    }
    private void initialization() {

        mauth=FirebaseAuth.getInstance();
        codes=(EditText)findViewById(R.id.verificationcode);
        b1=(Button)findViewById(R.id.submit);
        phone=getIntent().getStringExtra("phone");
        usename=getIntent().getStringExtra("username");
        usepass=getIntent().getStringExtra("password");
        useid=getIntent().getStringExtra("userId");
        empty=getIntent().getStringExtra("phonenull");

        progressDialog=new ProgressDialog(CodeVerification.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Loading...");
    }
    private void CodeCkeck(String code){
        PhoneAuthCredential php= PhoneAuthProvider.getCredential(VerificationId,code);
        SigininCredential(php);
    }
    private void SigininCredential(PhoneAuthCredential php) {
        mauth.signInWithCredential(php).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String,Object> Account=new HashMap<>();
                    Account.put("UserName", usename);
                    Account.put("UserId", useid);
                    Account.put("Password",usepass);
                    Account.put("PhoneNumber",empty);
                    Account.put("ReferenceId","Nil");
                    Account.put("TargetReferenceId","Nil");
                    Account.put("ReferenceCount","0");
                    Account.put("Balance","Nil");
                    Account.put("Time","Nil");
                    Account.put("BankName","Nil");
                    Account.put("AccountNumber","Nil");
                    Account.put("AccountHolderName","Nil");
                    Account.put("Ifsc","Nil");
                    Account.put("Branch","Nil");
                    Account.put("TotalAmount","0");
                    Account.put("MoneyCount","0.001388889");
                    Account.put("Date",date);
                    Account.put("Leader",totalamount);
                    Account.put("AccountStatus","false");
                    fb.collection("adsZworld_Account").document(useid).set(Account).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                    fb.collection("adsZworld_Account1").document(useid).set(Account).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    Map<String,Object> auth=new HashMap<>();
                    auth.put("PhoneNumber",empty);
                    {
                        Map<String, Object> user = new HashMap<>();
                        user.put("UserName", usename);
                        user.put("UserId", useid);
                        user.put("Password",usepass);
                        user.put("PhoneNumber",empty);

                        fb.collection("adsZworld_Auth").document(useid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Successfully Registered Login to use UserId and Password",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(CodeVerification.this,MainActivity.class);
                                progressDialog.cancel();
                                startActivity(i);
                                finish();
                            }
                        });
                        fb.collection("adsZworld_Auth1").document(useid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Successfully Registered Login to use UserId and Password",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(CodeVerification.this,MainActivity.class);
                                progressDialog.cancel();
                                startActivity(i);
                                finish();
                            }
                        });
                        reference.child(useid).setValue(Account).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                        reference.child(useid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void   SendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                CodeVerification.this,
                Mcallback
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks Mcallback=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    VerificationId=s;
                }
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    progressDialog.show();
                    String code=phoneAuthCredential.getSmsCode();
                    codes.setText(code);
                    if(code!=null){
                        progressDialog.show();
                        CodeCkeck(code);
                        codes.setText(code);
                    }
                }
                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            };

}
