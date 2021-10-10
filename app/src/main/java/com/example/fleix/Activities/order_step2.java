package com.example.fleix.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Class.OrderPost;
import com.example.fleix.R;

import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class order_step2 extends AppCompatActivity{

    EditText sender_name,sender_location,date_picker_dialog;
    PinView sender_number,sender_pin;
    Button POST_ORDER,UserDetails,SPEED_ORDER;
    DatePickerDialog.OnDateSetListener setListener;

    public static OrderPost orderPost=new OrderPost();

    int OrderID_decimal;
    String OrderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_step2);
        hook();


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
                Random random=new Random();
                OrderID_decimal=random.nextInt(2147483647);
                OrderID=Integer.toHexString(OrderID_decimal);
                buttonFun();
            }
        });

        SPEED_ORDER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                OrderID_decimal=random.nextInt(21474836);
                OrderID=Integer.toHexString(OrderID_decimal);
                buttonFun();
            }
        });

        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        final Integer[] month = {calendar.get(Calendar.MONTH)};
        int date=calendar.get(Calendar.DAY_OF_MONTH);

        date_picker_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(order_step2.this,
                        setListener,
                        year,
                        month[0],
                        date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                month[0] = month[0] +1;
                String date_s=date+"/"+ month[0] +"/"+year;
                orderPost.setPickUpDate(date_s);
                date_picker_dialog.setText(date_s);
            }
        };
    }

    private void buttonFun() {
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
            orderPost.setStatus("ORDER PLACED");
            orderPost.setOrderId(OrderID);
            PostingDetails();
        }
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
        date_picker_dialog=findViewById(R.id.date_picker_dialog);
        SPEED_ORDER=findViewById(R.id.SPEED_ORDER);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}