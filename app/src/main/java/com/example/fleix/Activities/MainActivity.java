package com.example.fleix.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fleix.R;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    GifImageView image;
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("SharedPreferences",MODE_PRIVATE);
        hook();
        if(sharedPreferences.getString("Status","LOGOUT").equals("LOGIN")){
            startActivity(new Intent(MainActivity.this,Home.class));
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                }
            }, 1000);
        }
    }
    private void hook() {
        image=findViewById(R.id.splash_image);
    }
}