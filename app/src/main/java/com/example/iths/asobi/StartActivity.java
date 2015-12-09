package com.example.iths.asobi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        Thread th = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    onPause();
                    startActivity(new Intent(getString(R.string.Main_Activity)));
                }
            }
        };
        th.start();

    }


    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    }

