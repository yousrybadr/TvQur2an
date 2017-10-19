package com.pentavalue.tvquran.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.datasorage.AppSharedPrefs;
import com.pentavalue.tvquran.datasorage.PrefsConstant;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String locale=Locale.getDefault().getLanguage();
        AppSharedPrefs.saveStringPrefs(PrefsConstant.LANG,locale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, ParentActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
