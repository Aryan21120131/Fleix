package com.example.fleix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fleix.Class.OrderDetails;
import com.example.fleix.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderDetails> orderDetailsList;
    Context context;

    public OrderAdapter(List<OrderDetails> orderDetailsList, Context context) {
        this.orderDetailsList = orderDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetails post=orderDetailsList.get(position);
        holder.OrderId.setText(post.getOrderId());
        holder.weight_user_card.setText("Weight"+ post.getWeight());
        holder.type_user_card.setText("Type: "+post.getType());
        holder.reciever_name_user_card.setText("Receiver name"+post.getRecieverName());
        holder.total_cost_user_card.setText(post.getTotalCost());
        holder.status_user_card.setText(post.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView    OrderId,
                weight_user_card,
                type_user_card,
                reciever_name_user_card,
                total_cost_user_card,
                status_user_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderId=itemView.findViewById(R.id.order_id_user_card);
            weight_user_card=itemView.findViewById(R.id.weight_user_card);
            type_user_card=itemView.findViewById(R.id.type_user_card);
            reciever_name_user_card=itemView.findViewById(R.id.reciever_name_user_card);
            total_cost_user_card=itemView.findViewById(R.id.total_cost_user_card);
            status_user_card=itemView.findViewById(R.id.status_user_card);
        }
    }
}
