package com.vjsm.sports.adszworld;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Video_Playing extends YouTubeBaseActivity implements RewardedVideoAdListener {

    private FirebaseFirestore fb=FirebaseFirestore.getInstance();
    private String UserId;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer.PlayerStyle playerStyle;
    YouTubePlayer.OnFullscreenListener onFullscreenListener;

    YouTubePlayerView myoutube;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String threada;
    ProgressDialog progressDialog;
    private String watch="false";
    private TextView textView;
    private double total_Count;
    InterstitialAd interstitialAd;
    private  String sss;
    private int s=2;
    private int moneycount;
    TextView Balance_name,BalanceCount;
    private   double Count=0;
    private   double moneyvalue=0;

    private CountDownTimer countDownTimer;
    RewardedVideoAd rewardedVideoAd;
    AdView adView,advie1,advie2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__playing);

        MobileAds.initialize(this,"ca-app-pub-2741745301323571~3063137643");
        rewardedVideoAd= MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);




        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        advie1=(AdView)findViewById(R.id.adView1);
        AdRequest adRequest1=new AdRequest.Builder().build();
        advie1.loadAd(adRequest1);
        advie2=(AdView)findViewById(R.id.adView2);
        AdRequest adRequest2=new AdRequest.Builder().build();
        advie2.loadAd(adRequest2);
        textView=(TextView)findViewById(R.id.balance_text);
        myoutube = (YouTubePlayerView) findViewById(R.id.view);
        Balance_name=(TextView)findViewById(R.id.balance_text) ;
        BalanceCount=(TextView)findViewById(R.id.balance_count) ;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences=getSharedPreferences("com.vjsm.sports.adszworld", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        UserId= sharedPreferences.getString(getString(R.string.UserId),"");

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2741745301323571/7050468200");
        interstitialAd.loadAd(new AdRequest.Builder().build());



        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, final boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.setShowFullscreenButton(false);
                if (!b) {
                    progressDialog.cancel();
                    youTubePlayer.loadVideo(getIntent().getStringExtra("video"));
                }

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {
                        rewardedVideoAd.show();

                        sss="End";

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {

                super.onAdClosed();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }
        });
    }

    private void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd("ca-app-pub-2741745301323571/1582535014",
                new AdRequest.Builder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        countDownTimer =new CountDownTimer(7000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (rewardedVideoAd.isLoaded()){
                    progressDialog.cancel();
                    rewardedVideoAd.show();
                    sss="Start";
                }
                else    {
                    myoutube.initialize(YoutubeApiConfig.getYoutube_API(),onInitializedListener);

                }

            }
        }.start();
        DocumentReference documentReference=fb.collection("adsZworld_Account").document(UserId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String Referencecount = snapshot.getString("ReferenceId");
                    DocumentReference documentReference1=fb.collection("Reference_Details").document(Referencecount);
                    documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot snapshot1=task.getResult();
                                moneycount=Integer.parseInt(snapshot1.getString("Count"));
                                if (moneycount<1){
                                    moneyvalue=0.002222222;
                                }
                                else if (moneycount<=4){
                                    moneyvalue=0.0025;
                                }
                                else if(moneycount<=8){
                                    moneyvalue=0.002777778;
                                }
                                else if (moneycount<=13){
                                    moneyvalue=0.003055556;
                                }else if (moneycount<=17){
                                    moneyvalue=0.003333333;
                                }else if (moneycount<=23){
                                    moneyvalue=0.003611111;
                                }else if (moneycount<=38){
                                    moneyvalue=0.003888889;
                                }else if (moneycount<=33){
                                    moneyvalue=0.004166667;
                                }else if(moneycount<=36){
                                    moneyvalue=0.004444444;
                                }else if(moneycount<=42){
                                    moneyvalue=0.004722222;
                                }else if (moneycount<=46){
                                    moneyvalue=0.005;
                                }else if (moneycount<=50){
                                    moneyvalue=0.005277778;
                                }
                                else {
                                    moneyvalue=0.005555556;
                                }
                            }
                        }
                    });


                }
            }
        });


        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String AccountStatus = snapshot.getString("AccountStatus");
                    String totlabalance = snapshot.getString("TotalAmount");
                    textView.setText("Balance :" + Double.parseDouble(totlabalance));
                    double tot = Double.parseDouble(snapshot.getString("TotalAmount"));
                    if (AccountStatus.equals("true")) {
                        final Thread thread = new Thread() {

                            @Override
                            public void run() {
                                try {
                                    while (!isInterrupted()) {
                                        Thread.sleep(1000);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Count=Count+moneyvalue;
                                                BalanceCount.setText(String.valueOf(Count));
                                                if (s<Count){
                                                    if(interstitialAd.isLoaded()){

                                                        interstitialAd.show();
                                                        interstitialAd.loadAd(new AdRequest.Builder().build());
                                                    }
                                                }


                                            }
                                        });
                                    }
                                } catch (InterruptedException e) {
                                }
                            }
                        };
                        thread.start();

                    } else {

                        BalanceCount.setText("Activate Account !");
                    }


                }
            }
        });

    }







    @Override
    protected void onResume() {
        super.onResume();
        loadRewardedVideoAd();
        rewardedVideoAd.resume(this);
        interstitialAd.loadAd(new AdRequest.Builder().build());

        Count=0;
        s=2;
        DocumentReference documentReference=fb.collection("adsZworld_Account").document(UserId);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                          @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


             if (task.isSuccessful()) {
                 DocumentSnapshot snapshot = task.getResult();
                 String AccountStatus = snapshot.getString("AccountStatus");
                 String totlabalance = snapshot.getString("TotalAmount");

                 textView.setText("Balance :" + Double.parseDouble(totlabalance));
                 double tot = Double.parseDouble(snapshot.getString("TotalAmount"));
             } }
        });
        Log.d("status_of_applicaiton", String.valueOf(Count));

        Log.d("status_of_applicaiton","onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        rewardedVideoAd.pause();

        final DocumentReference documentReference=fb.collection("adsZworld_Account").document(UserId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    String total_amount = snapshot.getString("TotalAmount");
                    double att=Double.parseDouble(total_amount);

                    double totla=att+Count;
                    interstitialAd.loadAd(new AdRequest.Builder().build());

                    documentReference.update("Leader",totla);

                    documentReference.update("TotalAmount",String.valueOf(totla)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Count=0;
                                s=2;

                            }
                        }
                    });

                }
            }
        });

        Log.d("status_of_applicaiton", String.valueOf(Count));

        Log.d("status_of_applicaiton","onPause");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("status_of_applicaiton","Onbackpressed");
        rewardedVideoAd.show();

        sss="Back";

    }

    @Override
    protected void onDestroy() {
        rewardedVideoAd.destroy(this);



        Log.d("status_of_applicaiton", String.valueOf(Count));

        super.onDestroy();
        Log.d("status_of_applicaiton","On Destroyed");
    }
    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d("status_of_ads","On loaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {

        Log.d("status_of_ads","On opend");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d("status_of_ads","On rewarded");
        loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d("status_of_ads","On Closed");
        loadRewardedVideoAd();
        if (sss.equals("Start")){
            if (watch.equals("false")){
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Don't close ad");
                builder.setMessage("Watch full ads after watch videos and earn money");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }


                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();


            }else if(watch.equals("true")){
                myoutube.initialize(YoutubeApiConfig.getYoutube_API(), onInitializedListener);

            }


        }else   if (sss.equals("End")){
            onDestroy();
            finish();
        }else if(sss.equals("Back"))  {
            finish();
        }

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        loadRewardedVideoAd();
        watch="true";

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        Log.d("status_of_ads","On comploadedleted");
        watch="true";
        loadRewardedVideoAd();
        if (sss.equals("Start")){


        }else   if (sss.equals("End")){
            onDestroy();
            finish();
        }else if(sss.equals("Back"))  {
            finish();
        }
    }


}
