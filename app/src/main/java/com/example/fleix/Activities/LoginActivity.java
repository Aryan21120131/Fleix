package com.example.fleix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Class.Portfolio;
import com.example.fleix.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Spinner role;
    String[] roles={"Customer","Admin","Felixian","Airport Manager"};
    ImageView image_role;
    Button next;
    String Role;
    PinView mobile_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hook();
        SetDrpoDown();
        next.setOnClickListener(view -> {
            Check();
        });
    }
    private void Check() {
        Call<List<Portfolio>> call1= RetrofitClient.getService().getPortfolioData();
        call1.enqueue(new Callback<List<Portfolio>>() {
            @Override
            public void onResponse(Call<List<Portfolio>> call, Response<List<Portfolio>> response) {
                List<Portfolio> list=response.body();
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getPhoneNumber().equals(mobile_number.getText().toString())&&
                            list.get(i).getRole().equals(Role)){
                        MainActivity.sharedPreferences.edit().putString("Status","LOGIN").commit();
                        MainActivity.sharedPreferences.edit().putString("Username",list.get(i).getUserName()).commit();
                        MainActivity.sharedPreferences.edit().putString("Role",list.get(i).getRole()).commit();
                        MainActivity.sharedPreferences.edit().putString("Email",list.get(i).getEmailId()).commit();
                        MainActivity.sharedPreferences.edit().putString("Address",list.get(i).getAddress()).commit();
                        MainActivity.sharedPreferences.edit().putString("PhoneNumber",mobile_number.getText().toString()).commit();
                        startActivity(new Intent(LoginActivity.this,Home.class));
                        Home.portfolio=list.get(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Portfolio>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SetDrpoDown() {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,roles);
        role.setAdapter(adapter);
        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:image_role.setImageResource(R.drawable.customer);
                        Role="Customer";
                        break;
                    case 1:image_role.setImageResource(R.drawable.manager);
                        Role="Admin";
                        break;
                    case 2:image_role.setImageResource(R.drawable.felixian);
                        Role="Felixian";
                        break;
                    case 3:image_role.setImageResource(R.drawable.airport_manager);
                        Role="Airport Manager";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void hook() {
        next=findViewById(R.id.next_login);
        role=findViewById(R.id.role_login);
        image_role=findViewById(R.id.image_role_login);
        mobile_number=findViewById(R.id.mobile_number_login);
        Role="USER";
    }
}