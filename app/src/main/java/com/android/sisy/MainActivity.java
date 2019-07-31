package com.android.sisy;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;

import com.android.ceritamahasiswaa.R;

public class MainActivity extends Activity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
