package com.example.fleix.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fleix.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class PickAddress extends AppCompatActivity {

    EditText pick;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_address);
        hook();
        Places.initialize(getApplicationContext(),"AIzaSyCqATIuI_rfm-SxrbxUZHV4qLy5VPCVS98");
        pick.setFocusable(false);
        pick.setOnClickListener(view -> {
            List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,
                    Place.Field.LAT_LNG,
                    Place.Field.NAME);
            Intent pick_intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                    fieldList).build(PickAddress.this);
            startActivityForResult(pick_intent,100);
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickAddress.this,Home.class));
            }
        });
    }

    private void hook() {
        pick=findViewById(R.id.pick_location);
        save=findViewById(R.id.Save);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Place place= Autocomplete.getPlaceFromIntent(data);
            pick.setText(place.getAddress());
        }else if(requestCode== AutocompleteActivity.RESULT_ERROR){
            Status status=Autocomplete.getStatusFromIntent(data);
            Toast.makeText(PickAddress.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}