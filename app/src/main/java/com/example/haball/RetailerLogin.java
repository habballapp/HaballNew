package com.example.haball;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

public class RetailerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.action_bar_main, null);

      //  btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

    }
}
