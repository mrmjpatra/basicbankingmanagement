package com.example.basicbankingapp.Model;

public class BankModel {
  String accountno,phoneno, name, balance, name1, name2, date, transaction_status;

    public BankModel(){

    }

    public BankModel(String accountno, String phoneno, String name, String balance) {
        this.accountno = accountno;
        this.phoneno = phoneno;
        this.name = name;
        this.balance = balance;
    }

    public BankModel(String name1, String name2,String balance, String date, String transaction_status) {
        this.name1 = name1;
        this.name2 = name2;
        this.balance = balance;
        this.date = date;
        this.transaction_status = transaction_status;
    }
    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getAccountno() {
        return accountno;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getName() {
        return name;
    }

    public String getBalance() {
        return balance;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getDate() {
        return date;
    }

    public String getTransaction_status() {
        return transaction_status;
    }
}
