package com.vjsm.sports.adszworld;

import android.app.ProgressDialog;
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

public class Video_Songs extends Fragment {
    ImageView count;
    private ImageView calender;
    private Y_Video_Songs_CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore fb;
    ProgressDialog progressDialog;
    private ArrayList<YUsers> personNames;
    private ProgressBar pro;
    private EditText searchView;
    String TAG = "hot";
    AdView adView;

    private RecyclerView mrecyclerView;
    String date;
    FirebaseDatabase firebaseDatabase;
    TextView vi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View v=   inflater.inflate(R.layout.video_songs,container,false);

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
        customAdapter = new Y_Video_Songs_CustomAdapter( personNames,getContext());
        final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        final String date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        customAdapter.setOnItemClickListener(Video_Songs.this);        recyclerView.setAdapter(customAdapter);
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
        fb.collection("adsZworld_Video_Songs").orderBy("Number", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
}
