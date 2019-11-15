package com.vjsm.sports.adszworld;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Intent.ACTION_VIEW;

public class Mix_Videos extends Fragment implements UpdateHelper.onUpdateCheckLinstener{
    ImageView count;
    private  static final int Item_per_ad=2;
    private static final String bannerad_id="ca-app-pub-3940256099942544/6300978111";
    private ImageView calender;
    private Y_Mix_Videos_CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    AdView adView;

    private FirebaseFirestore fb;
    ProgressDialog progressDialog;
    private ArrayList<YUsers> personNames;
    private ProgressBar pro;
    private EditText searchView;
    String TAG = "hot";
    private RecyclerView mrecyclerView;
    String date;
    FirebaseDatabase firebaseDatabase;
    TextView vi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.mix_videos,container,false);

        UpdateHelper.with(getContext()).onUpdateChecker(this).check();

        adView=(AdView)v.findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        fb = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings s=new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        fb.setFirestoreSettings(s);
        recyclerView =v. findViewById(R.id.recyclerView);
        personNames = new ArrayList<YUsers>();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        customAdapter = new Y_Mix_Videos_CustomAdapter( personNames,getContext());
        final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        final String date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        customAdapter.setOnItemClickListener(Mix_Videos.this);        recyclerView.setAdapter(customAdapter);
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH-1);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //   dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
            fb.collection("adsZworld_mix_videos").orderBy("Number", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        YUsers Yus = documentSnapshot.toObject(YUsers.class);
                        personNames.add(Yus);
                        progressDialog.cancel();
                        if (personNames.size() > 0) {
                        }
                        customAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onUpdateCheckListener(final String urlApp) {
        AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                .setTitle("New Version Available")
                .setMessage("Please Update...")
                .setCancelable(false)
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = urlApp;

                        Intent i = new Intent(ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        getActivity().finish();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                  }
                }).create();
        alertDialog.show();

    }
}
