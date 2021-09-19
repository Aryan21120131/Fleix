package com.example.fleix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Class.Portfolio;
import com.example.fleix.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileNumber extends AppCompatActivity {

    Button Verify;
    PinView ph_no;
    public  static int random_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);
        hook();
        Verify.setOnClickListener(view -> {
            Home.portfolio.setPhoneNumber(ph_no.getText().toString());
            if (ph_no.getText().toString().length() != 10) {
                Toast.makeText(MobileNumber.this, "Enter Valid Phone Number !!!", Toast.LENGTH_SHORT).show();
            } else {
                OTP();
            }
        });
    }

    private void OTP() {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            // Construct data
            String apiKey = "apikey=" + "Mzk3ODM5Nzg2MzZmNmI3NjU0NTk2ZjU4NTQ2MzYzNjc=";
            Random random=new Random();
            random_otp=random.nextInt(999999);
            String message = "&message=" + "Your Felix OTP CODE FOR NEW REGISTRATION IS "+random_otp;
            String sender = "&sender=" + "FELIX";
            String numbers = "&numbers=" + "91"+ph_no.getText().toString();

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
        } catch (Exception e) {
            Toast.makeText(MobileNumber.this, "Error"+e, Toast.LENGTH_SHORT).show();
            System.out.println("Error SMS "+e);
        }
        getData();
    }


    private void hook() {
        Verify=findViewById(R.id.next);
        ph_no=findViewById(R.id.reciever_no);
    }

    private void getData() {
        Call<List<Portfolio>> call1 = RetrofitClient.getService().getPortfolioData();
        call1.enqueue(new Callback<List<Portfolio>>() {
            @Override
            public void onResponse(Call<List<Portfolio>> call, Response<List<Portfolio>> response) {
                List<Portfolio> list = response.body();
                MainActivity.sharedPreferences.edit().putString("FOUND","NOT FOUND").commit();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getPhoneNumber().equals(ph_no.getText().toString())) {
                        MainActivity.sharedPreferences.edit().putString("FOUND","FOUND").commit();
                    }
                }
                if(MainActivity.sharedPreferences.getString("FOUND","NOT FOUND").equals("FOUND")){
                    Toast.makeText(MobileNumber.this, "Phone Number Already Used", Toast.LENGTH_SHORT).show();
                    ph_no.setText("");
                    startActivity(new Intent(MobileNumber.this,RegisterActivity.class));
                }else {
                    Home.portfolio.setPhoneNumber(ph_no.getText().toString());
                    MainActivity.sharedPreferences.edit().putString("PhoneNumber", ph_no.getText().toString()).commit();
                    Toast.makeText(MobileNumber.this, "OTP : " + random_otp, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MobileNumber.this, VerifyActivity.class));
                }
            }

            @Override
            public void onFailure(Call<List<Portfolio>> call, Throwable t) {

            }
        });
    }
}