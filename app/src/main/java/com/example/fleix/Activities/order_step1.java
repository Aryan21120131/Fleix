package com.example.fleix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.fleix.R;

public class order_step1 extends AppCompatActivity {

    EditText receiver_name,receiver_loc,length_order,width_order,height_order,weight_order,value_of_parcel;
    Spinner type_order;
    Button next_step;
    PinView receiver_no,receiver_pin;

    String[] types={"Box1","Sphere","Box2"};
    String type_string,dimensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_step1);
        hook();

        SetDropDown();
        next_step.setOnClickListener(view -> {
            startActivity(new Intent(order_step1.this,PickAddress.class));
            if(receiver_name.getText().toString().isEmpty()||
                    receiver_loc.getText().toString().isEmpty()||
                    length_order.getText().toString().isEmpty()||
                    width_order.getText().toString().isEmpty()||
                    height_order.getText().toString().isEmpty()||
                    weight_order.getText().toString().isEmpty()||
                    value_of_parcel.getText().toString().isEmpty()||
                    type_string.isEmpty()||
                    receiver_no.getText().toString().isEmpty()||
                    receiver_pin.getText().toString().isEmpty()){
                Toast.makeText(order_step1.this, "ALL FIELDS REQUIRED", Toast.LENGTH_SHORT).show();
            }else{
                dimensions=length_order.getText().toString()+
                        "X"+
                        width_order.getText().toString()+
                        "X"+
                        height_order.getText().toString();
                order_step2.orderPost.setRecieverName(receiver_name.getText().toString());
                order_step2.orderPost.setDropLocation(receiver_loc.getText().toString());
                order_step2.orderPost.setDimensions(dimensions);
                order_step2.orderPost.setWeight(weight_order.getText().toString());
                order_step2.orderPost.setValueOfParcel(value_of_parcel.getText().toString());
                order_step2.orderPost.setType(type_string);
                order_step2.orderPost.setRecieverPhoneNumber(receiver_no.getText().toString());
                order_step2.orderPost.setRecieverPinCode(receiver_pin.getText().toString());
                startActivity(new Intent(order_step1.this,order_step2.class));
            }
        });
    }

    private void SetDropDown() {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,types);
        type_order.setAdapter(adapter);
        type_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: type_string="BOX 1";
                        break;
                    case 1: type_string="SPHERE";
                        break;
                    case 2: type_string="BOX 2";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void hook() {
        receiver_name=findViewById(R.id.receiver_name);
        receiver_loc=findViewById(R.id.receiver_loc);
        length_order=findViewById(R.id.length_order);
        width_order=findViewById(R.id.width_order);
        height_order=findViewById(R.id.height_order);
        weight_order=findViewById(R.id.weight_order);
        value_of_parcel=findViewById(R.id.value_of_parcel);
        type_order=findViewById(R.id.type_order);
        next_step=findViewById(R.id.next_step);
        receiver_no=findViewById(R.id.receiver_no);
        receiver_pin=findViewById(R.id.receiver_pin);
    }
}