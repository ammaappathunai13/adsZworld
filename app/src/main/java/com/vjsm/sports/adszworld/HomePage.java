package com.vjsm.sports.adszworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import static android.content.Intent.ACTION_VIEW;

public class HomePage extends AppCompatActivity {
    private TextView mTextMessage;
    String accountPageCheck="";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mixvideos:
                    Fragment fragment=new Mix_Videos();
                    loadFragment(fragment);
                    return true;
                case R.id.comedy:
                    Fragment fragment1=new Comedy_Videos();
                    loadFragment(fragment1);
                    return true;
                case R.id.songs:
                    Fragment fragment2=new Video_Songs();
                    loadFragment(fragment2);
                    return true;
                case R.id.movies:
                    Fragment fragment3=new Movies();
                    loadFragment(fragment3);
                    return true;

                case R.id.account:
                    Fragment fragment4=new Account_Page();
                    loadFragment(fragment4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        accountPageCheck=getIntent().getStringExtra("re");
        String active=getIntent().getStringExtra("active");

        if (accountPageCheck!=null){
            Fragment acoount=new Account_Page();
            loadFragment(acoount);

        }else {
            Fragment fragment=new Mix_Videos();
            loadFragment(fragment);
        }


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

            return true;
        }
        return false;
    }


}
