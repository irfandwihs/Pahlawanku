package com.example.papermotion.pahlawanku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Paper Motion on 10/11/2017.
 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btn = (Button) findViewById(R.id.btnPlay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(HomeActivity.this, MainActivity.class);
                setContentView(R.layout.activity_main);
                startActivity(homeIntent);
                finish();
            }

        });
    }

    public void Keluar(View view) {
                finish(); //langsung keluar dari aplikasi
    }


}