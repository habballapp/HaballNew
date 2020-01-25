package com.example.haball.Language_Selection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.haball.R;
import com.example.haball.Register_Activity.Register_Activity;

public class Language_Selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__selection);


        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rel_english);

        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(Language_Selection.this, Register_Activity.class);
                startActivity(intent);
            }

        });
    }
}
