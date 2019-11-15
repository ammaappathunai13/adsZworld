package com.vjsm.sports.adszworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.concurrent.TimeUnit;

public class YoutubeApi extends YouTubeBaseActivity implements RewardedVideoAdListener{
    private SharedPreferences sharedPreferences;
    private EditText Username,Password;
    private SharedPreferences.Editor editor;
    private String UserId;
    private ProgressDialog progressDialog;
    private int adscount=0;
    private TextView textView;
    private boolean ads_Completed=false;
    private CountDownTimer countDownTimer;
    private  RewardedVideoAdListener rewardedVideoAdListener;
    YouTubePlayerView myoutube;
      YouTubePlayerView playerView;
      private FirebaseFirestore fb=FirebaseFirestore.getInstance();
      TextView Balance_name,BalanceCount;
      double Count=0;
    AdView adView,advie1,advie2;
    InterstitialAd interstitialAd;

    private double s=1;
    private  String sss;

    Button clickplay;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer.PlayerStyle playerStyle;
    YouTubePlayer.OnFullscreenListener onFullscreenListener;

    RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api);
        progressDialog=new ProgressDialog(YoutubeApi.this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        Balance_name=(TextView)findViewById(R.id.balance_text) ;
        BalanceCount=(TextView)findViewById(R.id.balance_count) ;
        myoutube=(YouTubePlayerView)findViewById(R.id.view);
        textView=(TextView)findViewById(R.id.balance_text);
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        advie1=(AdView)findViewById(R.id.adView1);
        AdRequest adRequest1=new AdRequest.Builder().build();
        advie1.loadAd(adRequest1);
        advie2=(AdView)findViewById(R.id.adView2);
        AdRequest adRequest2=new AdRequest.Builder().build();
        advie2.loadAd(adRequest2);


        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());




        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences=getSharedPreferences("com.vjsm.sports.adszworld", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
       UserId= sharedPreferences.getString(getString(R.string.UserId),"");


       MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
       rewardedVideoAd= MobileAds.getRewardedVideoAdInstance(this);
       rewardedVideoAd.setRewardedVideoAdListener(this);
       loadRewardedVideoAd();

        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.setShowFullscreenButton(false);
                youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);

                if (!b){
                    youTubePlayer.loadVideo(getIntent().getStringExtra("video"));
                    long mils=youTubePlayer.getCurrentTimeMillis();
                    long minite= TimeUnit.MILLISECONDS.toMinutes(mils);
                    Log.d("minites",String.valueOf(minite));
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

                        loadRewardedVideoAd();



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
        onFullscreenListener=new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {

            }
        };
    }
        private void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());

    }
    @Override
    protected void onStart() {
        super.onStart();

        startThread("start");
        DocumentReference documentReference=fb.collection("adsZworld_Account").document(UserId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    String AccountStatus = snapshot.getString("AccountStatus");
                    String totlabalance=snapshot.getString("TotalAmount");
                    DecimalFormat decimalFormat=new DecimalFormat("0.00");
                    textView.setText("Balance :"+Double.parseDouble(totlabalance));
                    double tot= Double.parseDouble(snapshot.getString("TotalAmount"));
                    if(AccountStatus.equals("true")){
                        final Thread thread=new Thread(){

                            @Override
                            public void run() {
                                try {
                                    while (!isInterrupted()) {
                                        Thread.sleep(1000);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Count=Count+0.01;
                                                BalanceCount.setText(String.valueOf(Count));
                                                if (s<Count){
                                                    if(interstitialAd.isLoaded()){

                                                        interstitialAd.show();
                                                        interstitialAd.loadAd(new AdRequest.Builder().build());
                                                        s=1;
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

                    }else
                    {

                        BalanceCount.setText("Activate Account !");
                    }


                }
            }
        });

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClicked() {
                super.onAdClicked();

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();


                Log.d("interstitial add","ad closed");

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("interstitial add","ad loaded");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();


            }
        });

    }

    private void startThread(final String value) {



    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("lockcheck","Phone is destroyed");

        rewardedVideoAd.destroy(this);
        final DocumentReference documentReference=fb.collection("adsZworld_Account").document(UserId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    String total_amount = snapshot.getString("TotalAmount");
                    double att=Double.parseDouble(total_amount);

                    double totla=att+Count;


                    documentReference.update("TotalAmount",totla);

                }
            }
        });
        Count=0;
        finish();


    }



    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d("adsss","loadded");

    }

    @Override
    public void onRewardedVideoAdOpened() {
        onPause();
        Log.d("adsss","opened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d("adsss","Started");

        loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoAdClosed() {

        if (sss.equals("Start")){
            myoutube.initialize(YoutubeApiConfig.getYoutube_API(), onInitializedListener);
        }else   if (sss.equals("End")){
            onDestroy();
        }else if(sss.equals("Back"))  {
            finish();
        }
        Log.d("adsss","closed");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Log.d("adsss","reward item");
        onResume();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d("adsss","left application");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.d("adsss","failde to load");
    }

    @Override
    public void onRewardedVideoCompleted() {
        if (sss.equals("Start")){
            myoutube.initialize(YoutubeApiConfig.getYoutube_API(), onInitializedListener);
        }else   if (sss.equals("End")){
            onDestroy();
        }else if(sss.equals("Back"))  {
            finish();
        }

        Log.d("adsss", "complete");




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
        rewardedVideoAd.show();
        sss="Back";
        ads_Completed=true;

        finish();
    }

}
