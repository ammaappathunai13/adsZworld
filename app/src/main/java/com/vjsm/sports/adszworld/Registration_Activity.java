package com.vjsm.sports.adszworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Registration_Activity extends AppCompatActivity {

    private EditText names,userids,phonenumbers,passwords,confirmpaswords;
    private String Name,UserId,Phonenumber,Password,ConfirmPassword;
    private ProgressDialog progressDialog;
    private Button Register;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
   private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
   private DatabaseReference reference=firebaseDatabase.getReference("Userdetail s");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);

        initialization();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                gettingvalues();
                Check_and_upload();
            }
        });
    }




    private void Check_and_upload() {
        if(TextUtils.isEmpty(Name)){
            names.setError("fill your Name");
            progressDialog.cancel();

        }else if(TextUtils.isEmpty(UserId)){
            userids.setError("Fill UserID");
            progressDialog.cancel();
        }
        else if(TextUtils.isEmpty(Phonenumber)){
            phonenumbers.setError("Fill 10 digit Phone number");
            progressDialog.cancel();
        }else if(Phonenumber.length()<10){
            phonenumbers.setError("Fill 10 digit Phone number");
            progressDialog.cancel();
        }
        else if(TextUtils.isEmpty(Password)){
            passwords.setError("Fill PassWord");
            progressDialog.cancel();
        }else if(Password.length()<5){
            passwords.setError("Password allowed above 5 characters");
            progressDialog.cancel();
        }
        else if(TextUtils.isEmpty(ConfirmPassword)){
            confirmpaswords.setError("Fill Confirm Password");
            progressDialog.cancel();
        }
        else if(!Password.equals(ConfirmPassword)){
            passwords.setError("Fill correct Password ");
            progressDialog.cancel();
            Toast.makeText(getApplicationContext(),"Password not same",Toast.LENGTH_LONG).show();
            passwords.setText("");
            confirmpaswords.setText("");
        }
        else
        {


            CollectionReference clf = firebaseFirestore.collection("adsZworld_Auth");
            Query q1 = clf.whereEqualTo("UserId", UserId);
            q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    boolean isExisting = false;
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String Numbers = documentSnapshot.getString("UserId");
                        if(UserId.equals(Numbers)){
                            isExisting=true;
                            userids.setError("This UserId already taken.");
                            progressDialog.cancel();
                        }
                    }
                    if(!isExisting){
                        String ph = "+91" + Phonenumber;
                        progressDialog.cancel();
                        Intent send = new Intent(Registration_Activity.this, CodeVerification.class);
                        send.putExtra("phone", ph);
                        send.putExtra("phonenull",Phonenumber);
                        send.putExtra("username",Name);
                        send.putExtra("password",Password);
                        send.putExtra("userId",UserId);
                        startActivity(send);
                        finish();
                    }
                }
            });





            }

    }

    private void gettingvalues() {

        Name=names.getText().toString().trim();
        UserId=userids.getText().toString().trim();
        Password=passwords.getText().toString().trim();
        ConfirmPassword=confirmpaswords.getText().toString().trim();
        Phonenumber=phonenumbers.getText().toString().trim();
        return;

    }

    private void initialization() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        names=(EditText)findViewById(R.id.name_registration);
        userids=(EditText)findViewById(R.id.userID_Registration);
        phonenumbers=(EditText)findViewById(R.id.phonenumber_registration);
        passwords=(EditText)findViewById(R.id.password_registration);
        confirmpaswords=(EditText)findViewById(R.id.confirm_password_registration);
        Register=(Button)findViewById(R.id.register_registration);

    }
}
