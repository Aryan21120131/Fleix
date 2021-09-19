package com.example.fleix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fleix.Class.OrderDetails;
import com.example.fleix.R;

import java.util.List;
import java.util.Locale;

public class FelixStarAdapter extends RecyclerView.Adapter<FelixStarAdapter.FelixStarHolder> {

    private List<OrderDetails> orderDetailsList;
    Context context;

    public FelixStarAdapter(List<OrderDetails> orderDetailsList, Context context) {
        this.orderDetailsList = orderDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public FelixStarAdapter.FelixStarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.non_user_item,parent,false);
        return new FelixStarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FelixStarAdapter.FelixStarHolder holder, int position) {
        OrderDetails post=orderDetailsList.get(position);
        holder.Order_ID.setText("ORDER ID: "+post.getOrderId().toUpperCase(Locale.ROOT));
        holder.RecieverName_NonUser_Card.setText(post.getRecieverName());
        holder.From_NonUser_card.setText(post.getPickLocation());
        holder.To_NonUser_Card.setText(post.getDropLocation());
        holder.PhoneNumber_NonUser_Card.setText(post.getRecieverPhoneNumber());
        holder.Update_Status_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(post.getStatus().equals("ORDER INITIATED")){
//                    OrderPost orderPost=new OrderPost();
//                    orderPost.setRecieverName(post.getRecieverName());
//                    orderPost.setDropLocation(post.getDropLocation());
//                    orderPost.setPickLocation(post.getPickLocation());
//                    orderPost.setOrderId(post.getOrderId());
//                    orderPost.setType(post.getType());
//                    orderPost.setWeight(post.getWeight());
//                    orderPost.setDimensions(post.getDimensions());
//                    orderPost.setTotalCost(post.getTotalCost());
//                    orderPost.setStatus("Picked Up");
//                    orderPost.setRecieverPinCode(post.getRecieverPinCode());
//                    orderPost.setRecieverPhoneNumber(post.getRecieverPhoneNumber());
//                    orderPost.setValueOfParcel(post.getValueOfParcel());
//                    orderPost.setUserName(post.getUserName());
//                    orderPost.setSenderPhoneNumber(post.getSenderPhoneNumber());
//                    orderPost.setPinCode(post.getPinCode());
//                    orderPost.setPickUpDate(post.getPickUpDate());
//                    orderPost.setPickUpTime(post.getPickUpTime());
//                    Call<OrderDetails> call= RetrofitClient.getService().update(post.getId(),orderPost);
//                    call.enqueue(new Callback<OrderDetails>() {
//                        @Override
//                        public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
//                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<OrderDetails> call, Throwable t) {
//
//                        }
//                    });
//                }else{
//                    Call<Void> call=RetrofitClient.getService().delete(post.getId());
//                    call.enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            Toast.makeText(context, "Delivered", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//
//                        }
//                    });
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class FelixStarHolder extends RecyclerView.ViewHolder {
        TextView Order_ID,
                RecieverName_NonUser_Card,
                From_NonUser_card,
                To_NonUser_Card,
                PhoneNumber_NonUser_Card;
        Button Update_Status_card;

        public FelixStarHolder(@NonNull View itemView) {
            super(itemView);
            Order_ID=itemView.findViewById(R.id.Order_ID_NonUser_Card);
            RecieverName_NonUser_Card=itemView.findViewById(R.id.RecieverName_NonUser_Card);
            From_NonUser_card=itemView.findViewById(R.id.From_NonUser_card);
            To_NonUser_Card=itemView.findViewById(R.id.To_NonUser_Card);
            PhoneNumber_NonUser_Card=itemView.findViewById(R.id.PhoneNumber_NonUser_Card);
            Update_Status_card=itemView.findViewById(R.id.Update_Status_card);
        }
    }
}
