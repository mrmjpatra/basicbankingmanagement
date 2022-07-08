package com.example.basicbankingapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.AllUserList;
import com.example.basicbankingapp.DataBaseHelper;
import com.example.basicbankingapp.Model.BankModel;
import com.example.basicbankingapp.R;
import com.example.basicbankingapp.Activity.SendMoneyUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SendMoneyAdapter extends RecyclerView.Adapter<SendMoneyAdapter.ViewHolder> {
    List<BankModel> modelList;
    Context context;
    //Passing intent varibles
    String name, currentamount, transferamount, remainingamount,senderaccountnumber;
    //Selected custom variables
    String selectuser_accountnumber, selectuser_name, selectuser_balance, date;
    Intent intent;

    public void setFilterdList(List<BankModel> filteredList){
        this.modelList=filteredList;
        notifyDataSetChanged();
    }

    public SendMoneyAdapter(List<BankModel> modelList, Context context,String name,String currentamount,String transferamount,String senderaccountnumber) {
        this.modelList = modelList;
        this.context = context;
        this.name=name;
        this.currentamount=currentamount;
        this.transferamount=transferamount;
        this.senderaccountnumber=senderaccountnumber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.send_money_user_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(modelList.get(position).getName());
        holder.useraccountnumber.setText(modelList.get(position).getAccountno());
        holder.balance.setText(modelList.get(position).getBalance());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");

                date = simpleDateFormat.format(calendar.getTime());
                selectuser_accountnumber=modelList.get(position).getAccountno();
                Cursor cursor = new DataBaseHelper(context).readparticulardata(selectuser_accountnumber);
                while(cursor.moveToNext()) {
                        selectuser_name=cursor.getString(1);
                        selectuser_balance=cursor.getString(2);
                        Double dcurrentbalance=Double.parseDouble(selectuser_balance);
                        Double dtransferbalance=Double.parseDouble(transferamount);
                        Double dtotalbalance=dcurrentbalance+dtransferbalance;

                        Log.d("Send Money Adapter","Current balance :"+dcurrentbalance.toString()+"Transafer balance"+dtransferbalance.toString()+"Total balance :"+dtotalbalance.toString());
                        new DataBaseHelper(context).insertTransferData(date,name,selectuser_name,transferamount,"SUCCESS");
                        new DataBaseHelper(context).updateAmount(selectuser_accountnumber,dtotalbalance.toString());
                   Log.d("Send Money Adapter","Total balance is "+dtotalbalance.toString()+"Calling calculate method");
                    calculateAmount();
                  intent=new Intent(context,AllUserList.class);
                    Log.d("Send Money Adapter","Receiver : "+selectuser_name+"Balance :"+selectuser_balance+"A/C No "+selectuser_accountnumber);
                    Log.d("Send Money Adapter","Sender:"+name+"Balance :"+currentamount+"Transfer Amount: "+transferamount);
                    //Custom Dialog box
                    showDialog();
                }
            }
        });

    }

    private void showDialog() {
        Dialog dialog = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.setCancelable(false);//You can't cancel the dialog by clicking in the black space around the dialog
        Button btnOkay = dialog.findViewById(R.id.btnOkay);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                context.startActivity(intent);
                ((SendMoneyUser)context).finish();
            }
        });
        dialog.show();
    }

    private void calculateAmount() {
        Log.d("Send Money Adapter","Inside the Calculate Method");
        Log.d("Send Money Adapter","Sender current:"+currentamount+"Transfer amount"+transferamount);
        Double Dcurrentamount = Double.parseDouble(currentamount);
        Double Dtransferamount = Double.parseDouble(transferamount);
        Double Dremainingamount = Dcurrentamount - Dtransferamount;
        remainingamount = Dremainingamount.toString();
        new DataBaseHelper(context).updateAmount(senderaccountnumber,remainingamount);
        Log.d("Send Money Adapter","Leaving the Calculate Method");

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,useraccountnumber,balance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            useraccountnumber=itemView.findViewById(R.id.useraccountnumber);
            balance=itemView.findViewById(R.id.balance);
        }
    }
}
