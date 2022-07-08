package com.example.basicbankingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.basicbankingapp.DataBaseHelper;
import com.example.basicbankingapp.R;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;

public class UserData extends AppCompatActivity {
    TextView name, phoneNumber, email, account_no, ifsc_code, balance;
    MaterialCardView transfer_button;
    String accountnumber;
    Double newbalance;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        getSupportActionBar().setTitle("Account Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.username);
        phoneNumber = findViewById(R.id.userphonenumber);
        email = findViewById(R.id.email);
        account_no = findViewById(R.id.account_no);
        ifsc_code = findViewById(R.id.ifsc_code);
        balance = findViewById(R.id.balance);
        transfer_button=findViewById(R.id.transfer_button);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            accountnumber = bundle.getString("accountnumber");
            showData(accountnumber);
        }
        transfer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAmount();
            }
        });

    }

    private void enterAmount() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserData.this);
        View mView = getLayoutInflater().inflate(R.layout.transfer_money_dialog, null);
        mBuilder.setTitle("Enter amount").setView(mView).setCancelable(false);
        final EditText mAmount = (EditText) mView.findViewById(R.id.enter_money);
        mBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAmount.getText().toString().isEmpty()){
                    mAmount.setError("Amount can't be empty");
                }else if(Double.parseDouble(mAmount.getText().toString()) > newbalance){
                    mAmount.setError("Your account don't have enough balance");
                }else if (Double.parseDouble(mAmount.getText().toString())==0){
                    mAmount.setError("Your  can't send zero amount");
                } else{
                        Intent intent = new Intent(UserData.this, SendMoneyUser.class);
                        intent.putExtra("accountnumber", account_no.getText().toString());
                        intent.putExtra("name", name.getText().toString());
                        intent.putExtra("currentamount", newbalance.toString());
                        intent.putExtra("transferamount", mAmount.getText().toString());
                        startActivity(intent);
                        finish();
                }
            }
        });

    }

    private void showData(String accountnumber) {

        Cursor cursor = new DataBaseHelper(this).readparticulardata(accountnumber);
        while(cursor.moveToNext()) {
            String balancefromdb = cursor.getString(2);
            newbalance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(newbalance);

            phoneNumber.setText(cursor.getString(5));
            name.setText(cursor.getString(1));
            email.setText(cursor.getString(3));
            balance.setText(price);
            account_no.setText(cursor.getString(0));
            ifsc_code.setText(cursor.getString(4));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}