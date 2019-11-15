package com.vjsm.sports.adszworld;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pusher.pushnotifications.reporting.OpenNotificationActivity;

import static android.support.constraint.Constraints.TAG;

public class Account_Page extends Fragment {
    private TextView UserName,UserId,Phonenumber,Usernamebleow,Yourreferences,yourreferencecode,AvailableBalance,Marquee;
    private TextView Bankname,AccountNumber,AccountHoldername,Ifsc,BrachName;
    private Button Referesh,ActivateAccount,BankdetailsUpload,Changebankdetails,Signout;
    private String userName,userId,phoneNumber;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirebaseFirestore fb=FirebaseFirestore.getInstance();
    private TextView help_video_for_Registration;
    private DocumentReference getbankdetails;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private String banknamecheck,AccountStatus,ReferenceCode,ReferenceCount,available;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.account_page,container,false);

            progressDialog =new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");







//initilize widgets

        Marquee=(TextView)v.findViewById(R.id.marque);
        help_video_for_Registration=(TextView)v.findViewById(R.id.register_And_signin);

        Usernamebleow=(TextView)v.findViewById(R.id.username_account_page);
        UserId=(TextView)v.findViewById(R.id.userid_Account_page);
        UserName=(TextView)v.findViewById(R.id.username_top_account_page);
        Phonenumber=(TextView)v.findViewById(R.id.phonenumber_AccountPage);
        Referesh=(Button)v.findViewById(R.id.referesh);
        ActivateAccount=(Button)v.findViewById(R.id.activate_reference_id_Account_page);
        BankdetailsUpload=(Button)v.findViewById(R.id.add_bank_details);
        Changebankdetails=(Button)v.findViewById(R.id.change_Bank_details);
        Signout=(Button)v.findViewById(R.id.SignOut);
        Bankname=(TextView)v.findViewById(R.id.bankname_account_page);
        AccountNumber=(TextView)v.findViewById(R.id.account_number_account_page);
        AccountHoldername=(TextView)v.findViewById(R.id.account_Holder_name_Account_page);
        Ifsc=(TextView)v.findViewById(R.id.ifsc_account_page);
        BrachName=(TextView)v.findViewById(R.id.branch_account_page);
        yourreferencecode=(TextView)v.findViewById(R.id.reference_code_show);
        Yourreferences=(TextView)v.findViewById(R.id.totalreference);
        AvailableBalance=(TextView)v.findViewById(R.id.available_balance_account_page);

        alertDialog=new AlertDialog.Builder(getActivity());
        Marquee.setSelected(true);


//getvalues from shared preference
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences=getActivity().getSharedPreferences("com.vjsm.sports.adszworld", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //assign strings


        userId=sharedPreferences.getString(getString(R.string.UserId),"");
        phoneNumber=sharedPreferences.getString(getString(R.string.LoginUser_PhoneNumber),"");
        userName=sharedPreferences.getString(getString(R.string.LoginUserName),"");



        getbankdetails=fb.collection("adsZworld_Account").document(userId);
        DocumentReference reference=fb.collection("marque").document("marquee");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    Marquee.setText(snapshot.getString("marquee"));

                    Log.d(TAG, "onComplete: "+snapshot.getString("marquee"));
                }
            }
        });


        getbankdetails.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    progressDialog.show();

                    final DocumentSnapshot snapshot=task.getResult();
                    banknamecheck=snapshot.getString("BankName");
                    AccountStatus=snapshot.getString("AccountStatus");
                    ReferenceCode=snapshot.getString("ReferenceId");

                    available=snapshot.getString("TotalAmount");
                  final   String Banknames=snapshot.getString("BankName");
                    DocumentReference documentReference=fb.collection("Reference_Details").document(ReferenceCode);

                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){


                                DocumentSnapshot snapshot1=task.getResult();
                                String count=snapshot1.getString("Count");
                                Yourreferences.setText("Your References : "+count);

                            }
                        }
                    });  help_video_for_Registration.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent ss=new Intent(getContext(),Help_Videos.class);

                            startActivity(ss);
                        }
                    });



                    if (AccountStatus.equals("true")){
                      ActivateAccount.setVisibility(View.INVISIBLE);
                      yourreferencecode.setText("Your Reference ID : "+ReferenceCode);
                      AvailableBalance.setText("Balance : "+available);
                  }

                    if (!banknamecheck.equals("Nil")){
                        Bankname.setText(snapshot.getString("BankName"));
                        Bankname.setVisibility(View.VISIBLE);
                        AccountNumber.setText(snapshot.getString("AccountNumber"));
                        AccountNumber.setVisibility(View.VISIBLE);
                        AccountHoldername.setText(snapshot.getString("AccountHolderName"));
                        AccountHoldername.setVisibility(View.VISIBLE);
                        Ifsc.setText(snapshot.getString("Ifsc"));
                        Ifsc.setVisibility(View.VISIBLE);
                        BrachName.setText(snapshot.getString("Branch"));
                        BrachName.setVisibility(View.VISIBLE);
                        BankdetailsUpload.setVisibility(View.INVISIBLE);
                        Changebankdetails.setVisibility(View.VISIBLE);
                        progressDialog.cancel();


                    }else{
                        BankdetailsUpload.setVisibility(View.VISIBLE);
                        Changebankdetails.setVisibility(View.INVISIBLE);
                        progressDialog.cancel();
                    }




                }
            }
        });
        //set values to text view
        UserId.setText("UserID :"+userId);
        Phonenumber.setText("Phone :"+phoneNumber);
        UserName.setText("Hi "+userName+" !");
        Usernamebleow.setText("UserName :"+userName);

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                Intent s=new Intent(getContext(),MainActivity.class);
                startActivity(s);
                getActivity().finish();
            }
        });

        Changebankdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ss=new Intent(getContext(),Bank_details_Upload.class);
                ss.putExtra("check","update");
                startActivity(ss);
            }
        });

        BankdetailsUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent ss=new Intent(getContext(),Bank_details_Upload.class);
            ss.putExtra("check","add");
            startActivity(ss);
            }
        });

        Referesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent ss=new Intent(getContext(),HomePage.class);
                ss.putExtra("re","re");
                startActivity(ss);
            }
        });
        ActivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                getbankdetails=fb.collection("adsZworld_Account").document(userId);

                getbankdetails.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){

                            DocumentSnapshot snapshot=task.getResult();
                            banknamecheck=snapshot.getString("BankName");
                            final   String Banknames=snapshot.getString("BankName");
                            if (!banknamecheck.equals("Nil")){
                                Bankname.setText(snapshot.getString("BankName"));
                                Bankname.setVisibility(View.VISIBLE);
                                AccountNumber.setText(snapshot.getString("AccountNumber"));
                                AccountNumber.setVisibility(View.VISIBLE);
                                AccountHoldername.setText(snapshot.getString("AccountHolderName"));
                                AccountHoldername.setVisibility(View.VISIBLE);
                                Ifsc.setText(snapshot.getString("Ifsc"));
                                Ifsc.setVisibility(View.VISIBLE);
                                BrachName.setText(snapshot.getString("Branch"));
                                BrachName.setVisibility(View.VISIBLE);
                                progressDialog.cancel();


                            }
                            if (banknamecheck.equals("Nil")){
                                progressDialog.cancel();

                                alertDialog.setTitle("Alert !");
                                alertDialog.setMessage("Please Provide Bank Details after activate your account");
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }

                                });
                                AlertDialog alertDialog1=alertDialog.create();
                                alertDialog1.show();

                            }else{
                                Intent s=new Intent(getContext(),Account_Activation.class);
                                startActivity(s);
                            }




                        }
                    }
                });




            }
        });



        return v;
    }

    private void sharedPrferencegetvalues() {

    }

    private void widgetsInit() {

    }

}
