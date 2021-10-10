package com.example.fleix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fleix.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView image_role;
    Button next;
    String Role;
    EditText user,email,address;
    TextView Move_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        hook();
        Move_login.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this,LoginActivity.class)));

        next.setOnClickListener(view -> {
            if(!(user.getText().toString().isEmpty()
                    ||Role.isEmpty()
                    ||email.getText().toString().isEmpty()
                    ||address.getText().toString().isEmpty())){
                    SetData();
            }
            if(user.getText().toString().isEmpty()){
                user.setText("");
                user.setHint("Required a valid USERNAME");
            }
            if(email.getText().toString().isEmpty()){
                email.setText("");
                email.setHint("Required a valid EMAIL");
            }
            if(address.getText().toString().isEmpty()){
                address.setText("");
                address.setHint("Required a valid ADDRESS");
            }
        });
    }

    private void hook() {
        image_role=findViewById(R.id.image_role_register);
        next=findViewById(R.id.next_register);
        user=findViewById(R.id.username_register);
        email=findViewById(R.id.email_register);
        address=findViewById(R.id.address_register);
        Role="Customer";
        Move_login=findViewById(R.id.move_login);
    }

    private void SetData() {
        MainActivity.sharedPreferences.edit().putString("Username",user.getText().toString()).commit();
        MainActivity.sharedPreferences.edit().putString("Role",Role).commit();
        MainActivity.sharedPreferences.edit().putString("Email",email.getText().toString()).commit();
        MainActivity.sharedPreferences.edit().putString("Address",address.getText().toString()).commit();
        Home.portfolio.setUserName(user.getText().toString());
        Home.portfolio.setRole(Role);
        Home.portfolio.setEmailId(email.getText().toString());
        Home.portfolio.setAddress(address.getText().toString());
        startActivity(new Intent(RegisterActivity.this,MobileNumber.class));
    }
}