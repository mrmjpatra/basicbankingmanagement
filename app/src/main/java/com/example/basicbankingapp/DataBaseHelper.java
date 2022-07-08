package com.example.basicbankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.basicbankingapp.Model.BankModel;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="BankDB";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE1="user_table";
    public static final String TABLE2="transaction_table";

    //COLUMNS OF TABLE1

    private static final String KEY_ACCOUNTNO="ACCOUNT_NO";
    private static final String KEY_NAME="NAME";
    private static final String KEY_BALANCE="BALANCE";
    private static final String KEY_EMAIL="EMAIL";
    private static final String KEY_IFSC="IFSC_CODE";
    private static final String KEY_PHONE_NO="PHONE_NUMBER";

    //COLUMNS OF TABLE 2
    private static final String KEY_TRANSACTIONID="TRANSACTIONID";
    private static final String KEY_DATE="DATE";
    private static final String KEY_FROMNAME="FROMNAME";
    private static final String KEY_TONAME="TONAME";
    private static final String KEY_STATUS="STATUS";
    private static final String KEY_AMOUNT="AMOUNT";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATING TABLE2
        db.execSQL("CREATE TABLE "+TABLE1+ "(" + KEY_ACCOUNTNO + " INTEGER PRIMARY KEY, "+ KEY_NAME +" TEXT,"+KEY_BALANCE+" DECIMAL, "+ KEY_EMAIL +" VARCHAR, "+KEY_IFSC +" VARCHAR, "+ KEY_PHONE_NO + " TEXT" + ")");


        //CREATING TABLE2
        db.execSQL("CREATE TABLE "+ TABLE2+ "("+ KEY_TRANSACTIONID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_DATE+" TEXT,"+KEY_FROMNAME+" TEXT,"+KEY_TONAME+" TEXT,"+KEY_BALANCE+ " DECIMAL,"+KEY_STATUS+" TEXT"+ ")");
//       db.execSQL("create table " + TABLE2 +" (TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FROMNAME TEXT,TONAME TEXT,AMOUNT DECIMAL,STATUS TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE2);
        onCreate(db);
    }



    //ADDING DATE INTO TABLE 1
    public  void addData(String account,String name,String balance,String email, String ifsc, String phone_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ACCOUNTNO, account);
        values.put(KEY_NAME, name);
        values.put(KEY_BALANCE, balance);
        values.put(KEY_EMAIL, email);
        values.put(KEY_IFSC, ifsc);
        values.put(KEY_PHONE_NO, phone_no);
        db.insert(TABLE1, null, values);

    }


    public Cursor readAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE1, null);
        return cursor;
    }


    //FETCHING ALL THE DATA FROM THE TABLE1 AND RETURN ARRAYLIST
    public ArrayList<BankModel> fetchContact(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE1, null);
        ArrayList<BankModel> arrContacts=new ArrayList<>();
        while (cursor.moveToNext()){
            String balancefromdb = cursor.getString(5);
            Double balance   = Double.parseDouble(balancefromdb);
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);
            BankModel model=new BankModel(cursor.getString(0),price,cursor.getString(1),cursor.getString(2));
            arrContacts.add(model);
        }
        return arrContacts;
    }


    //FETCHING THE DETAILS OF THE PARTICULAR USER WITH ACCOUNT NUMBER
    public Cursor readparticulardata(String accountnumber){
        Log.d("read particular data",accountnumber);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE1+" WHERE "+ KEY_ACCOUNTNO+" = " +accountnumber, null);
        return cursor;
    }
    //For send money user
    public Cursor readselectuserdata(String accountnumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE1+" except select * from user_table where "+KEY_ACCOUNTNO+" = " +accountnumber, null);
        return cursor;
    }
    //Update the debit and credit amount to the customer
    public void updateAmount(String accountnumber, String amount){
        Log.d("Update amount","Updating"+accountnumber+"AMount "+amount);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+TABLE1 +" set "+KEY_BALANCE+" = " + amount + " WHERE "+ KEY_ACCOUNTNO+" = " +accountnumber);
    }


    //Read the data from the table2

    public Cursor readtransferdata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE2+" ORDER BY "+KEY_DATE +" DESC",null);
        return cursor;
    }
    //Insert the data into the table2
    public boolean insertTransferData(String date,String from_name, String to_name, String amount, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_FROMNAME, from_name);
        contentValues.put(KEY_TONAME, to_name);
        contentValues.put(KEY_BALANCE, amount);
        contentValues.put(KEY_STATUS, status);
        Long result = db.insert(TABLE2, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

}
