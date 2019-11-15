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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Bank_details_Upload extends AppCompatActivity {

    private EditText BankName,AccountNumber,AccountHolderName,IFSC,BranchName;
    private String bankName,accounNumber,accountHolderName,ifsc,branchName;
    private Button add;
    private FirebaseFirestore fb=FirebaseFirestore.getInstance();
    private TextView help_video_for_Registration;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView title;
    private  String userId;
    private String checkk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details__upload);

        initwidgets();
        checkk=getIntent().getStringExtra("check");

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences=getSharedPreferences("com.vjsm.sports.adszworld", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        userId=sharedPreferences.getString(getString(R.string.UserId),"");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        if(checkk.equals("add")){
            add.setText("ADD DETAILS");
            title.setText("Add Bank Details");
        }
        if (checkk.equals("update"))
        {
            add.setText("UPDATE DETAILS");
            title.setText("Update Bank Details");
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                getEdittextdetails();
                CheckDetails_and_Upload();
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

    private void CheckDetails_and_Upload() {

        if (TextUtils.isEmpty(bankName)){
            BankName.setError("Fill Bank name");
            progressDialog.cancel();
        }else if(TextUtils.isEmpty(accounNumber)){
            AccountNumber.setError("Fill Account Number");
            progressDialog.cancel();
        }else if(TextUtils.isEmpty(accountHolderName)){
            AccountHolderName.setError("Fill Account Holder Name");
            progressDialog.cancel();

        }else if(TextUtils.isEmpty(ifsc)){
            IFSC.setError("Fill IFSC ");
            progressDialog.cancel();
        }else if(TextUtils.isEmpty(branchName)){
            BranchName.setError("Fill Branch Name");
            progressDialog.cancel();
        }
        else {
            DocumentReference documentReference=fb.collection("adsZworld_Account").document(userId);

                documentReference.update("BankName",bankName);
                documentReference.update("AccountNumber",accounNumber);
                documentReference.update("AccountHolderName",accountHolderName);
                documentReference.update("Ifsc",ifsc);
                documentReference.update("Branch",branchName).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    progressDialog.cancel();
                        AlertDialog.Builder builder=new AlertDialog.Builder(Bank_details_Upload.this);
                        builder.setMessage("Bank Details Added Successfully. ");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }
                });
        }

    }

    private void getEdittextdetails() {

        bankName=BankName.getText().toString().trim();
        accounNumber=AccountNumber.getText().toString().trim();
        accountHolderName=AccountHolderName.getText().toString().trim();
        ifsc=IFSC.getText().toString().trim();
        branchName=BranchName.getText().toString().trim();
    }

    private void initwidgets() {
        BankName=(EditText)findViewById(R.id.bank_name_Bank_details);
        AccountNumber=(EditText)findViewById(R.id.account_number_Bank_details);
        AccountHolderName=(EditText)findViewById(R.id.Account_Holeder_Name_Bank_details);
        IFSC=(EditText)findViewById(R.id.ifsc_Bank_details);
        BranchName=(EditText)findViewById(R.id.Branch_bank_details);
        add=(Button)findViewById(R.id.add_bank_details);
        title=(TextView)findViewById(R.id.bankdetails_title);
        help_video_for_Registration=(TextView)findViewById(R.id.register_And_signin);
    }
}
