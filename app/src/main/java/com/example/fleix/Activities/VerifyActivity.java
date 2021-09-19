package com.example.fleix.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Class.Portfolio;
import com.example.fleix.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity {

    PinView pin;
    Button Login;
    TextView Text;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        hook();
        Text.setText("OTP SEND TO PHONE NUMBER \n"+Home.portfolio.getPhoneNumber());
        Login.setOnClickListener(view -> {
            setData();
//            if(pin.getText().toString().equals(String.valueOf(MobileNumber.random_otp))){
//                setData();
//                startActivity(new Intent(VerifyActivity.this,Home.class));
//            }else{
//                Toast.makeText(VerifyActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
//            }
        });
    }

    private void setData() {
        if(isOnline()) {
            Call<Portfolio> call = RetrofitClient.getService().postPortfolio(Home.portfolio);
            call.enqueue(new Callback<Portfolio>() {
                @Override
                public void onResponse(Call<Portfolio> call, Response<Portfolio> response) {
                    if (response.isSuccessful()) {
                        MainActivity.sharedPreferences.edit().putString("Status","LOGIN").commit();
                        startActivity(new Intent(VerifyActivity.this, Home.class));
                    } else {
                        Toast.makeText(VerifyActivity.this, response.code() + "\n" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Portfolio> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(VerifyActivity.this, "OFFLINE !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isOnline() {
        try {
            ConnectivityManager manager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=null;
            if(manager!=null){
                networkInfo=manager.getActiveNetworkInfo();
            }
            return networkInfo!=null && networkInfo.isConnected();
        }catch (Exception e){
            return false;
        }
    }

    private void hook() {
        pin=findViewById(R.id.otp_code);
        Login=findViewById(R.id.login_btn);
        Text=findViewById(R.id.OTP_NUMBER_TEXT);
    }
}