package com.example.fleix.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Adapters.Adapter;
import com.example.fleix.Adapters.FelixStarAdapter;
import com.example.fleix.Adapters.OrderAdapter;
import com.example.fleix.Class.OrderDetails;
import com.example.fleix.Class.Portfolio;
import com.example.fleix.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    private RecyclerView recyclerView;
    FloatingActionButton add;
    LinearLayoutManager layoutManager;
    String role;

    public static Portfolio portfolio=new Portfolio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hook();
        setNav();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,order_step1.class));
            }
        });
    }

    private void setAdminRecyclerView() {
        Call<List<OrderDetails>> call= RetrofitClient.getService().getOrderData();
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                List<OrderDetails> orderPostList=response.body();
                layoutManager = new LinearLayoutManager(Home.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                Adapter adapter = new Adapter(getApplicationContext(), orderPostList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {

            }
        });
    }

    private void setFelixStarRecyclerView() {
        Call<List<OrderDetails>> call= RetrofitClient.getService().getOrderData();
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                List<OrderDetails> orderPostList=response.body();
                List<OrderDetails> orderDetails=new ArrayList<>();
                for(int i=0;i<orderPostList.size();i++){
                    String Status=orderPostList.get(i).getStatus();
                    Toast.makeText(Home.this, Status, Toast.LENGTH_SHORT).show();
                    if(Status.equals("ORDER INITIATED")||Status.equals("Out for Delivery")){
                        orderDetails.add(orderPostList.get(i));
                    }
                }
                layoutManager = new LinearLayoutManager(Home.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                FelixStarAdapter felixStarAdapter = new FelixStarAdapter(orderDetails, getApplicationContext());
                recyclerView.setAdapter(felixStarAdapter);
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {

            }
        });
    }

    private void setCoustmerRecyclerView() {
        Call<List<OrderDetails>> call= RetrofitClient.getService().getOrderData();
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                List<OrderDetails> orderPostList=response.body();
                List<OrderDetails> orderDetails=new ArrayList<>();
                for(int i=0;i<orderPostList.size();i++){
                    String name1=orderPostList.get(i).getUserName(),
                            name2=MainActivity.sharedPreferences.getString("Username",portfolio.getUserName());
                    if(name1.equals(name2)){
                        orderDetails.add(orderPostList.get(i));
                    }
                }
                layoutManager = new LinearLayoutManager(Home.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                OrderAdapter adapter = new OrderAdapter(orderDetails, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {

            }
        });
    }

    private void setNav() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_email:
                    try {
                        Intent email_intent=new Intent(Intent.ACTION_SENDTO);
                        String[] to={"Felix-dh@outlook.com"};
                        email_intent.setData(Uri.parse("mailto:"));
                        email_intent.putExtra(Intent.EXTRA_EMAIL,to);
                        startActivity(Intent.createChooser(email_intent,"Send Email"));
                        break;
                    }catch (Exception e){

                    }
                case R.id.menu_call:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + "9938813445"));
                    startActivity(intent);
                    break;
                case R.id.menu_sms:
                    Intent sms_intent = new Intent(Intent.ACTION_VIEW);
                    sms_intent.setType("vnd.android-dir/mms-sms");
                    sms_intent.setData(Uri.parse("sms:" + "9938813445"));
                    startActivity(sms_intent);
                    break;
                case R.id.menu_logout:
                    MainActivity.sharedPreferences.edit().putString("Status","LOGOUT").commit();
                    startActivity(new Intent(Home.this,RegisterActivity.class));
                    break;
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        Toolbar toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setTitle("FELIX");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);
        TextView name_nav = headerView.findViewById(R.id.username_nav);
        ImageView img_nav=headerView.findViewById(R.id.image_nav);
        TextView phone_nav=headerView.findViewById(R.id.phone_nav);
        name_nav.setText(MainActivity.sharedPreferences.getString("Username",portfolio.getUserName()));
        phone_nav.setText(MainActivity.sharedPreferences.getString("PhoneNumber",portfolio.getPhoneNumber()));
        switch(role){
            case "Coustmer":img_nav.setImageResource(R.drawable.user_logo);
                add.setVisibility(View.VISIBLE);
                setCoustmerRecyclerView();
                break;
            case "Felix Star":img_nav.setImageResource(R.drawable.delivery_boy_logo);
                Toast.makeText(Home.this, "FELIX STAR", Toast.LENGTH_SHORT).show();
                add.setVisibility(View.INVISIBLE);
                setFelixStarRecyclerView();
                break;
            case "Admin":img_nav.setImageResource(R.drawable.manager_logo);
                Toast.makeText(Home.this, "ADMIN", Toast.LENGTH_SHORT).show();
                add.setVisibility(View.INVISIBLE);
                setAdminRecyclerView();
                break;
        }
    }

    private void hook() {
        navigationView = findViewById(R.id.navigation_drawer);
        recyclerView=findViewById(R.id.user_recycle);
        add=findViewById(R.id.new_order);
        role=MainActivity.sharedPreferences.getString("Role","Coustmer");
    }
}