package com.example.fleix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Class.OrderPost;
import com.example.fleix.R;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class order_step2 extends AppCompatActivity{

    EditText sender_name,sender_location;
    PinView sender_number,sender_pin;
    Button POST_ORDER,UserDetails;

    public static OrderPost orderPost=new OrderPost();

    int OrderID_decimal;
    String OrderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_step2);
        hook();

        Random random=new Random();
        OrderID_decimal=random.nextInt(2147483647);
        OrderID=Integer.toHexString(OrderID_decimal);
        Toast.makeText(order_step2.this, OrderID, Toast.LENGTH_SHORT).show();

        UserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sender_name.setText(MainActivity.sharedPreferences.getString("Username","USERNAME"));
                sender_location.setText(MainActivity.sharedPreferences.getString("Address","Address"));
                sender_number.setText(MainActivity.sharedPreferences.getString("Phone Number","Phone Number"));
            }
        });

        POST_ORDER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sender_name.getText().toString().isEmpty()||
                        sender_location.getText().toString().isEmpty()||
                        sender_number.getText().toString().isEmpty()||
                        sender_pin.getText().toString().isEmpty()){
                    Toast.makeText(order_step2.this, "ALL FIELDS REQUIRED", Toast.LENGTH_SHORT).show();
                }else{
                    orderPost.setUserName(sender_name.getText().toString());
                    orderPost.setPickLocation(sender_location.getText().toString());
                    orderPost.setSenderPhoneNumber(sender_number.getText().toString());
                    orderPost.setPinCode(sender_pin.getText().toString());
                    orderPost.setStatus("ORDER INITIATED");
                    orderPost.setOrderId(OrderID);
                    PostingDetails();
                }
            }
        });
    }

    private void PostingDetails() {
        Call<OrderPost> call= RetrofitClient.getService().postOrder(orderPost);
        call.enqueue(new Callback<OrderPost>() {
            @Override
            public void onResponse(Call<OrderPost> call, Response<OrderPost> response) {
                if(response.isSuccessful()){
                    Toast.makeText(order_step2.this, "POSTED ORDER", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(order_step2.this,Home.class));
                }else{
                    Toast.makeText(order_step2.this, response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderPost> call, Throwable t) {

            }
        });
    }

    private void hook() {
        sender_name=findViewById(R.id.sender_name);
        sender_location=findViewById(R.id.sender_location);
        sender_number=findViewById(R.id.sender_number);
        sender_pin=findViewById(R.id.sender_pin);
        POST_ORDER=findViewById(R.id.POST_ORDER);
        UserDetails=findViewById(R.id.user_details);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}