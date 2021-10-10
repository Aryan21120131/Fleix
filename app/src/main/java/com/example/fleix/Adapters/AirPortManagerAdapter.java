package com.example.fleix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.view.PinView;
import com.example.fleix.API_DATA.RetrofitClient;
import com.example.fleix.Class.OrderDetails;
import com.example.fleix.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirPortManagerAdapter extends RecyclerView.Adapter<AirPortManagerAdapter.AirPortManagerAdapterHolder>{

    private List<OrderDetails> orderDetailsList;
    private String[] FelixianList;
    Context context;

    public AirPortManagerAdapter(List<OrderDetails> orderDetailsList, String[] felixianList, Context context) {
        this.orderDetailsList = orderDetailsList;
        FelixianList = felixianList;
        this.context = context;
    }

    @NonNull
    @Override
    public AirPortManagerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item,parent,false);
        return new AirPortManagerAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirPortManagerAdapterHolder holder, int position) {
        OrderDetails post=orderDetailsList.get(position);
        holder.AdminRecieverName.setText("Receiver name "+post.getRecieverName());
        holder.AdminDropLocation.setText("Drop Location "+post.getDropLocation());
        holder.AdminPickLocation.setText("Pick Location "+post.getPickLocation());
        holder.AdminOrderId.setText("Order Id "+post.getOrderId());
        holder.AdminType.setText("Type "+post.getType());
        holder.AdminWeight.setText("Weight "+post.getWeight());
        holder.AdminDimensions.setText("Dimension "+post.getDimensions());
        holder.AdminStatus.setText("Status "+post.getStatus());
        holder.AdminRecieverPinCode.setText("Receiver Pin Code "+post.getRecieverPinCode());
        holder.AdminRecieverPhoneNumber.setText("Receiver Phone Number "+post.getRecieverPhoneNumber());
        holder.AdminUserName.setText("User Name "+post.getUserName());
        holder.AdminSenderPhoneNumber.setText("Sender Phone Number "+post.getSenderPhoneNumber());
        holder.AdminPinCode.setText("Pin Code "+post.getPinCode());
        holder.AdminPickUpDate.setText("Pick Up Date "+post.getPickUpDate());
        holder.AdminOrderAssignId.setText("Order Assign ID "+post.getOrderAssignId());
        switch (post.getStatus()){
            case "OUT FOR SOURCE AIR PORT":
                holder.flightCode.setVisibility(View.VISIBLE);
                holder.AdminButton.setText("Update : UPLOADING AT AIRCRAFT");
                post.setStatus("UPLOADING AT AIRCRAFT");
                break;
            case "UPLOADING AT AIRCRAFT":
                holder.flightCode.setVisibility(View.INVISIBLE);
                holder.AdminButton.setText("Update : RECEIVING FROM AIRCRAFT");
                post.setStatus("RECEIVING FROM AIRCRAFT");
                break;
        }
        int id = post.getId();
        holder.AdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OrderDetails> call = RetrofitClient.getService().update(id, post);
                call.enqueue(new Callback<OrderDetails>() {
                    @Override
                    public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                        Toast.makeText(context, " " + response.message(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OrderDetails> call, Throwable t) {

                    }
                });
            }
        });

    }

    private String setDropDown(Spinner AdminFelixianAssignment) {
        final String[] Felixian = {null};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, FelixianList);
        AdminFelixianAssignment.setAdapter(adapter);
        AdminFelixianAssignment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Felixian[0] =FelixianList[0];
                        break;
                    case 1:
                        Felixian[0] =FelixianList[1];
                        break;
                    case 2:
                        Felixian[0] =FelixianList[2];
                        break;
                    case 3:
                        Felixian[0] =FelixianList[3];
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        String a = "<A>_<"+ Felixian[0] + ">";
        return a;
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class AirPortManagerAdapterHolder extends RecyclerView.ViewHolder{
        TextView AdminRecieverName,
                AdminDropLocation,
                AdminPickLocation,
                AdminOrderId,
                AdminType,
                AdminWeight,
                AdminDimensions,
                AdminStatus,
                AdminRecieverPinCode,
                AdminRecieverPhoneNumber,
                AdminUserName,
                AdminSenderPhoneNumber,
                AdminPinCode,
                AdminPickUpDate,
                AdminOrderAssignId;
        Spinner AdminFelixianAsignment;
        PinView flightCode;
        Button AdminButton;
        public AirPortManagerAdapterHolder(@NonNull View itemView) {
            super(itemView);
            AdminRecieverName=itemView.findViewById(R.id.AdminRecieverName);
            AdminDropLocation=itemView.findViewById(R.id.AdminDropLocation);
            AdminPickLocation=itemView.findViewById(R.id.AdminPickLocation);
            AdminOrderId=itemView.findViewById(R.id.AdminOrderId);
            AdminType=itemView.findViewById(R.id.AdminType);
            AdminWeight=itemView.findViewById(R.id.AdminWeight);
            AdminDimensions=itemView.findViewById(R.id.AdminDimensions);
            AdminStatus=itemView.findViewById(R.id.AdminStatus);
            AdminRecieverPinCode=itemView.findViewById(R.id.AdminRecieverPinCode);
            AdminRecieverPhoneNumber=itemView.findViewById(R.id.AdminRecieverPhoneNumber);
            AdminUserName=itemView.findViewById(R.id.AdminUserName);
            AdminSenderPhoneNumber=itemView.findViewById(R.id.AdminSenderPhoneNumber);
            AdminPinCode=itemView.findViewById(R.id.AdminPinCode);
            AdminPickUpDate=itemView.findViewById(R.id.AdminPickUpDate);
            AdminOrderAssignId=itemView.findViewById(R.id.AdminOrderAssignId);
            AdminFelixianAsignment=itemView.findViewById(R.id.AdminFelixianAsignment);
            AdminButton=itemView.findViewById(R.id.AdminButton);
            flightCode=itemView.findViewById(R.id.flightCode);
        }
    }
}
