package com.example.basicbankingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.Model.BankModel;
import com.example.basicbankingapp.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    Context context;
   List<BankModel> transactionmodelist;



    public TransactionHistoryAdapter(Context context, List<BankModel> transactionmodelist) {
        this.context = context;
        this.transactionmodelist = transactionmodelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.transaction_history_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.from_name.setText(transactionmodelist.get(position).getName1());
        holder.to_name.setText(transactionmodelist.get(position).getName2());
        holder.balance.setText(transactionmodelist.get(position).getBalance());
        holder.transaction_status.setText(transactionmodelist.get(position).getTransaction_status());
        holder.date.setText(transactionmodelist.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return transactionmodelist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView from_name,to_name,balance,transaction_status,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from_name=itemView.findViewById(R.id.from_name);
            to_name=itemView.findViewById(R.id.to_name);
            balance=itemView.findViewById(R.id.balance);
            transaction_status=itemView.findViewById(R.id.transaction_status);
            date=itemView.findViewById(R.id.date);
        }
    }
}
