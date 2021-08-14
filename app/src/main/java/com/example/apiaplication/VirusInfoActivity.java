package com.example.apiaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class VirusInfoActivity extends AppCompatActivity {

    private ImageView btn_back;
    private TextView Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virus_info);

        btn_back = findViewById(R.id.img_back);
        btn_back.setOnClickListener(this::finish);
        Info = findViewById(R.id.tv_info);
        Info.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void finish(View view) {
        finish();
    }

}