package com.example.fleix.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Adapters.AdminAdapter;
import com.example.fleix.Adapters.AirPortManagerAdapter;
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
    SwipeRefreshLayout refreshLayout;

    public static Portfolio portfolio=new Portfolio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hook();
        setNav();
        refreshLayout.setOnRefreshListener(() -> {
            setRecyclerViewHome();
            refreshLayout.setRefreshing(false);
        });
        if(role.equals("Customer")){
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(view -> startActivity(new Intent(Home.this,order_step1.class)));
        }else {
            add.setVisibility(View.INVISIBLE);
        }
    }

    private void setAdminRecyclerView() {
        Call<List<OrderDetails>> call= RetrofitClient.getService().getOrderData();
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                List<OrderDetails> orderPostList=response.body();
                List<OrderDetails> orderDetails=new ArrayList<>();
//                for(int i=0;i<orderPostList.size();i++){
//                    String Status=orderPostList.get(i).getStatus();
//                    if(Status.equals("ORDER PLACED")||
//                            Status.equals("Picked Up")||
//                            Status.equals("COLLECTED AT WAREHOUSE")||
//                            Status.equals("RECEIVING FROM AIRCRAFT")||
//                            Status.equals("COLLECTED AT RECEIVER STATION")){
//                        orderDetails.add(orderPostList.get(i));
//                    }
//                }
                String[] FelixianList={"000001 : Aman Gupta",
                        "000002 : Aryan Sharma",
                        "000003 : Arya Vats",
                        "000004 : Devansh Shrivastav"};
                layoutManager = new LinearLayoutManager(Home.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                AdminAdapter adapter = new AdminAdapter(orderPostList, getApplicationContext(),FelixianList);
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
                    if(Status.equals("ORDER INITIATED")||Status.equals("OUT FOR DELIVERY")){
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

    private void setAirPortManagerRecyclerView() {
        Call<List<OrderDetails>> call=RetrofitClient.getService().getOrderData();
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                List<OrderDetails> orderPostList=response.body();
                List<OrderDetails> orderDetails=new ArrayList<>();
                for(int i=0;i<orderPostList.size();i++){
                    String Status=orderPostList.get(i).getStatus();
                    if(Status.equals("OUT FOR SOURCE AIR PORT")||Status.equals("UPLOADING AT AIRCRAFT")){
                        orderDetails.add(orderPostList.get(i));
                    }
                }
                layoutManager = new LinearLayoutManager(Home.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                String[] FelixianList={"000001 : Aman Gupta",
                        "000002 : Aryan Sharma",
                        "000003 : Arya Vats",
                        "000004 : Devansh Shrivastav"};
                AirPortManagerAdapter felixStarAdapter = new AirPortManagerAdapter(orderDetails,FelixianList,getApplicationContext());
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
        setRecyclerViewHome();
        switch(role){
            case "Customer":img_nav.setImageResource(R.drawable.customer);
                add.setVisibility(View.VISIBLE);
                break;
            case "Airport Manager":img_nav.setImageResource(R.drawable.airport_manager);
                add.setVisibility(View.INVISIBLE);
                break;
            case "Admin":img_nav.setImageResource(R.drawable.manager);
                add.setVisibility(View.INVISIBLE);
                break;
            case "Felixian":img_nav.setImageResource(R.drawable.felixian);
                add.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void setRecyclerViewHome() {
        switch(role){
            case "Customer":setCoustmerRecyclerView();
                break;
            case "Felixian":setFelixStarRecyclerView();
                break;
            case "Admin":setAdminRecyclerView();
                break;
            case "Airport Manager":setAirPortManagerRecyclerView();
                break;
        }

    }

    private void hook() {
        navigationView = findViewById(R.id.navigation_drawer);
        recyclerView=findViewById(R.id.user_recycle);
        add=findViewById(R.id.new_order);
        role=MainActivity.sharedPreferences.getString("Role","Customer");
        refreshLayout=findViewById(R.id.SwipeRefreshLayout);
    }
}