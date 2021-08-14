package com.example.apiaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.apiaplication.fragment.CountryFragment;
import com.example.apiaplication.fragment.HomeFragment;
import com.example.apiaplication.module.Countries;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView title;
    private ImageView btn_menu;
    private BottomNavigationView navigation;
    private Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        title = findViewById(R.id.tv_title);
        btn_menu = findViewById(R.id.img_menu);
        navigation = findViewById(R.id.bottom_nav);

        btn_menu.setOnClickListener(this::showMenu);
        navigation.setOnNavigationItemSelectedListener(this);
        //Default Fragment
        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frame_lyt, selectedFragment
        ).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.main_home:
                selectedFragment = new HomeFragment();
                title.setText("صفحه اصلی");
                break;
            case R.id.country:
                selectedFragment = new CountryFragment();
                title.setText("کشورها");
                break;
            default:
                selectedFragment = new HomeFragment();
                title.setText("صفحه اصلی");
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frame_lyt, selectedFragment
        ).commit();
        return true;
    }

    private void showMenu(View btn) {
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, btn);
        MenuInflater mi = new MenuInflater(HomeActivity.this);
        mi.inflate(R.menu.more_info_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this::goNext);

        popupMenu.show();
    }

    private boolean goNext(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.contact_us) {
            startActivity(new Intent(HomeActivity.this, ContactActivity.class));
        }
        else if(menuItem.getItemId()==R.id.about_app){
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        }
        return true;
    }

}