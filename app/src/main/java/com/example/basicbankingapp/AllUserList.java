package com.example.basicbankingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.Activity.TransactionHistoryActivity;
import com.example.basicbankingapp.Adapter.CustomAdapterUserList;
import com.example.basicbankingapp.Model.BankModel;

import java.util.ArrayList;
import java.util.List;

public class AllUserList extends AppCompatActivity {
    private static final String TAG = "user_list";
    List<BankModel> allUserlist = new ArrayList<>();
    RecyclerView allUserListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_list);

        getSupportActionBar().setTitle("All Customers");
        allUserListRecyclerView = findViewById(R.id.alluserlistrecyclerview);
        allUserListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        insertIntoDatbase(dbHelper);
        showData(dbHelper);
        allUserlist = dbHelper.fetchContact();
        CustomAdapterUserList adapterUserList = new CustomAdapterUserList(allUserlist, this);
        allUserListRecyclerView.setAdapter(adapterUserList);

    }

    private void showData(DataBaseHelper dbHelper) {
        allUserlist = dbHelper.fetchContact();
        for (int i = 0; i < allUserlist.size(); i++) {
            Log.d("Bank Datbase", "Name : " + allUserlist.get(i).getName() + ", Account No: " + allUserlist.get(i).getAccountno() + ", Balance: " + allUserlist.get(i).getBalance());
        }
    }


    private void insertIntoDatbase(DataBaseHelper dbHelper) {
        dbHelper.addData("1000212176890", "MRUTYUNJAY", "5000", "mrmjpatar@gmail.com", "ORBC0101", "986178223");
        dbHelper.addData("3452938898900", "KRITI", "20000", "kriti@gmail.com", "ORBC0101", "7989901243");
        dbHelper.addData("8437578978789", "JANMEJAYA", "8000", "janmejaya@gmail.com", "ORBC0101", "8917564344");
        dbHelper.addData("9834538459223", "MANAS", "7000", "manas@gmail.com", "ORBC0101", "9861466791");
        dbHelper.addData("9587430983334", "SOUMYA", "15000", "soumya65@gmail.com", "ORBC0101", "8765412903");
        dbHelper.addData("3489573495833", "BISWA", "500000", "biswa7878@gmail.com", "ORBC0101", "6754389021");
        dbHelper.addData("5438957934878", "SUNIL", "10000", "sunilsahoo76@gmail.com", "ORBC0101", "8902432435");
        dbHelper.addData("7982789534111", "JASOBANTA", "12000", "jasobantasaa8@gmail.com", "ORBC0101", "8934563452");
        dbHelper.addData("3453894579224", "SURAJ", "50000", "surajsubudy76@gmail.com", "ORBC0101", "7377234524");
        dbHelper.addData("5734589396789", "PAPU", "20000", "papusahoo@gmail.com", "ORBC0101", "7879324234");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_history) {
            Intent transactionintent=new Intent(AllUserList.this, TransactionHistoryActivity.class);
            startActivity(transactionintent);
        }
        return super.onOptionsItemSelected(item);
    }

}