package com.example.basicbankingapp.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.Adapter.SendMoneyAdapter;
import com.example.basicbankingapp.DataBaseHelper;
import com.example.basicbankingapp.Model.BankModel;
import com.example.basicbankingapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SendMoneyUser extends AppCompatActivity {

    RecyclerView recyclerview;
    List<BankModel> allUserlist = new ArrayList<>();
    SendMoneyAdapter sendMoneyAdapter;
    String accountnumber, name, currentamount, transferamount, remainingamount;
    SearchView searchView;
    public static final String TAG = "Send Money";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_user);
        getSupportActionBar().hide();
        recyclerview = findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            accountnumber = bundle.getString("accountnumber");
            name = bundle.getString("name");
            currentamount = bundle.getString("currentamount");
            transferamount = bundle.getString("transferamount");
            Log.d(TAG, "Account No:" + accountnumber + " name :" + name + " current Amount: " + currentamount + " Transfer amount " + transferamount);
            showData(accountnumber);

        }
    }

    private void filterList(String text) {
        List<BankModel> filteredList = new ArrayList<>();
        for (BankModel item : allUserlist) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            sendMoneyAdapter.setFilterdList(filteredList);
        }

    }

    private void showData(String accountnumber) {
        Log.d(TAG, "Inside the show method | account number" + accountnumber);

        Cursor cursor = new DataBaseHelper(this).readselectuserdata(accountnumber);
        while (cursor.moveToNext()) {
            String balancefromdb = cursor.getString(2);
            Double balance = Double.parseDouble(balancefromdb);
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);
            BankModel model = new BankModel(cursor.getString(0), cursor.getString(5), cursor.getString(1), price);
            allUserlist.add(model);
        }

        sendMoneyAdapter = new SendMoneyAdapter(allUserlist, this, name, currentamount, transferamount, accountnumber);
        recyclerview.setAdapter(sendMoneyAdapter);

        for (int i = 0; i < allUserlist.size(); i++) {
            Log.d(TAG, "Account No : " + allUserlist.get(i).getAccountno() + ", Name: " + allUserlist.get(i).getName());
        }
        Log.d(TAG, "Leaving the show method");
    }

}