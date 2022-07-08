package com.example.basicbankingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.AllUserList;
import com.example.basicbankingapp.Model.BankModel;
import com.example.basicbankingapp.R;
import com.example.basicbankingapp.Activity.UserData;

import java.util.List;

public class CustomAdapterUserList extends RecyclerView.Adapter<CustomAdapterUserList.ViewHolder> {
    List<BankModel> modelList;
    Context context;
    String accountnumber;

    public CustomAdapterUserList(List<BankModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.userlist_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mName.setText(modelList.get(position).getName());
        holder.maccountnumber.setText(modelList.get(position).getAccountno());
        holder.mBalance.setText(modelList.get(position).getBalance());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Bank Model", "nextActivity: called");
                accountnumber = modelList.get(position).getAccountno();
                Intent intent = new Intent(context, UserData.class);
                intent.putExtra("accountnumber",accountnumber);
                Log.d("Account Number","Account number is"+modelList.get(position).getAccountno());
                context.startActivity(intent);
                ((AllUserList)context).finish();


            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName, maccountnumber, mBalance, mRupee, mRupeeslash, mName1, mName2, mDate, mTransc_status;
        ImageView mPhone, mArrow;
        View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mName = itemView.findViewById(R.id.username);
            maccountnumber = itemView.findViewById(R.id.maccountnumber);
            mBalance = itemView.findViewById(R.id.balance);
            mRupee = itemView.findViewById(R.id.rupee);
            mRupeeslash = itemView.findViewById(R.id.rupeeslash);
            mPhone = itemView.findViewById(R.id.phone);

        }
    }

}
