package com.example.fleix.Adapters;

import android.annotation.SuppressLint;
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

public class Adapter extends RecyclerView.Adapter<Adapter.holder> {

    Context context;
    List<OrderDetails> postList;

    public Adapter(Context context, List<OrderDetails> postList)
    {
        this.postList=postList;
        this.context=context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.non_user_item,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {
        OrderDetails post=postList.get(position);
        holder.Order_ID.setText(post.getOrderId());
        holder.RecieverName_NonUser_Card.setText(post.getRecieverName());
        holder.From_NonUser_card.setText(post.getPickLocation());
        holder.To_NonUser_Card.setText(post.getDropLocation());
        holder.PhoneNumber_NonUser_Card.setText(post.getRecieverPhoneNumber());
        holder.Update_Status_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class holder extends RecyclerView.ViewHolder{

        TextView Order_ID,
                RecieverName_NonUser_Card,
                From_NonUser_card,
                To_NonUser_Card,
                PhoneNumber_NonUser_Card;
        Button Update_Status_card;
        public holder(@NonNull View itemView) {
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
