package com.avash.tourstory.model;

public class ExpenseModel {
    private int exID;
    private int eID;
    private String title;
    private int amount;
    private String date;

    public ExpenseModel(int exID, int eID, String title, int amount, String date) {
        this.exID = exID;
        this.eID = eID;
        this.title = title;
        this.amount = amount;
        this.date = date;
    }
}
