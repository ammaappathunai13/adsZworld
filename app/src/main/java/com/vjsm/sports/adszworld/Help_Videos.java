package com.vjsm.sports.adszworld;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Help_Videos extends AppCompatActivity {
    ImageView imagev;
    ProgressBar progressBar;
    TextView Dec;
    private FirebaseFirestore fb=FirebaseFirestore.getInstance();
    String Description,VideoID;

    ImageView imagev1;
    ProgressBar progressBar1;
    TextView Dec1;
    String Description1,VideoID1;
    ImageView imagev2;
    ProgressBar progressBar2;
    TextView Dec2;
    String Description2,VideoID2;

    ImageView imagev3;
    ProgressBar progressBar3;
    TextView Dec3;
    String Description3,VideoID3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help__videos);
        imagev=(ImageView) findViewById(R.id.imagethump);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        Dec=(TextView)findViewById(R.id.titlee);
        imagev1=(ImageView) findViewById(R.id.imagethump1);
        progressBar1=(ProgressBar) findViewById(R.id.progressBar1);
        Dec1=(TextView)findViewById(R.id.titlee1);
        imagev2=(ImageView) findViewById(R.id.imagethump2);
        progressBar2=(ProgressBar) findViewById(R.id.progressBar2);
        Dec2=(TextView)findViewById(R.id.titlee2);
        imagev3=(ImageView) findViewById(R.id.imagethump3);
        progressBar3=(ProgressBar) findViewById(R.id.progressBar3);
        Dec3=(TextView)findViewById(R.id.titlee3);
        final DocumentReference reference=fb.collection("Help_videos").document("Registration");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();

             VideoID=   snapshot.getString("VideoUrl");
             Description=snapshot.getString("Dec");
             Dec.setText(Description);
                String url = "https://img.youtube.com/vi/"+VideoID+"/0.jpg";
                Glide.with(getApplicationContext()).load(url).into(imagev);
                imagev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent s=new Intent(getApplicationContext(),help_video_playing.class);
                        s.putExtra("VideoId",VideoID);
                        startActivity(s);
                    }
                });
            }
        });

        final DocumentReference reference1=fb.collection("Help_videos").document("Account_Activation");
        reference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();

                VideoID1=   snapshot.getString("VideoUrl");
                Description1=snapshot.getString("Dec");
                Dec1.setText(Description1);
                String url = "https://img.youtube.com/vi/"+VideoID1+"/0.jpg";
                Glide.with(getApplicationContext()).load(url).into(imagev1);
                imagev1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent s=new Intent(getApplicationContext(),help_video_playing.class);
                        s.putExtra("VideoId",VideoID1);
                        startActivity(s);
                    }
                });
            }
        });
        final DocumentReference reference2=fb.collection("Help_videos").document("Bank_Details");
        reference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();

                VideoID2=   snapshot.getString("VideoUrl");
                Description2=snapshot.getString("Dec");
                Dec2.setText(Description2);
                String url = "https://img.youtube.com/vi/"+VideoID2+"/0.jpg";
                Glide.with(getApplicationContext()).load(url).into(imagev2);
                imagev2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent s=new Intent(getApplicationContext(),help_video_playing.class);
                        s.putExtra("VideoId",VideoID2);
                        startActivity(s);
                    }
                });
            }
        });
        final DocumentReference reference3=fb.collection("Help_videos").document("Reference_details");
        reference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();

                VideoID3=   snapshot.getString("VideoUrl");
                Description3=snapshot.getString("Dec");
                Dec3.setText(Description3);
                String url = "https://img.youtube.com/vi/"+VideoID3+"/0.jpg";
                Glide.with(getApplicationContext()).load(url).into(imagev3);
                imagev3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent s=new Intent(getApplicationContext(),help_video_playing.class);
                        s.putExtra("VideoId",VideoID3);
                        startActivity(s);
                    }
                });
            }
        });






    }
}
