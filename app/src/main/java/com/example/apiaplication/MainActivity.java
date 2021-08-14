package com.example.apiaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.inter_view_anime);
        getInternetState();
    }

    private void getInternetState() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        if(manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
            manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
            showInterView();
        }
        else {
            animationView.setVisibility(View.GONE);
            showWarningDialog();
        }
    }

    private void showWarningDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_network_error);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button ok = dialog.findViewById(R.id.bt_finish_2);
        ok.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.create();
        dialog.show();
    }

    private void showInterView() {
        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        }, 2800);
    }
}