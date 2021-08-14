package com.example.apiaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class ContactActivity extends AppCompatActivity {
    private EditText fullName, subject, message;
    private ImageView backButton;
    private Button sendEmail;
    private String[] adminEmail = {"progali.info@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        fullName = findViewById(R.id.et_name);
        subject = findViewById(R.id.et_subject);
        message = findViewById(R.id.et_message);
        sendEmail = findViewById(R.id.bt_send_email);
        backButton = findViewById(R.id.img_back);

        backButton.setOnClickListener(this::backTo);
        sendEmail.setOnClickListener(this::sendEmail);
    }



    private void backTo(View view) {
        finish();
    }

    private void sendEmail(View view) {
        Intent email = new Intent(Intent.ACTION_SEND);

        email.putExtra(Intent.EXTRA_EMAIL, adminEmail);
        email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        email.putExtra(Intent.EXTRA_TEXT, fullName.getText().toString() + "\n\n" +message.getText().toString());

        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "برنامه Gmail (جیمیل) را برای ادمه کار انتخاب کنید"));
    }
}