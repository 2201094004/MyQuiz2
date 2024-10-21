package com.syaviraindahmaryam.myquiz2;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String date;
    private int amount;
    private String description;
    private boolean isIncome;  // Untuk membedakan pemasukan atau pengeluaran

    public Transaction(String date, int amount, String description, boolean isIncome) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.isIncome = isIncome;
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIncome() {
        return isIncome;
    }
}