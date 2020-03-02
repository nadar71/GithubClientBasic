package com.mapelli.simone.githubclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;

import com.mapelli.simone.githubclient.R;


/**
 * -------------------------------------------------------------------------------------------------
 * Minimal splash screen
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // startAppDirect();
        startAppWithDelay();
        loadingHandler();

    }

    private void startAppDirect(){
        Intent intent = new Intent(getApplicationContext(), SearchUsersActivity.class);
        startActivity(intent);
        finish();
    }

    private void startAppWithDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SearchUsersActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }


    // Loading indicator
    private void loadingHandler() {
        final TextView loadingTxt = findViewById(R.id.loading_splash_txt);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(loadingTxt.getText().length() > 10) {
                    loadingTxt.setText("Loading ");
                } else {
                    loadingTxt.setText(loadingTxt.getText()+".");
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);
    }
}
