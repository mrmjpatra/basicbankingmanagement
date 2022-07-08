package com.example.basicbankingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.basicbankingapp.AllUserList;
import com.example.basicbankingapp.R;

public class MainActivity extends AppCompatActivity {
    Button allusers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allusers=findViewById(R.id.allusers);
        allusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllUserList.class));
            }
        });
    }
}