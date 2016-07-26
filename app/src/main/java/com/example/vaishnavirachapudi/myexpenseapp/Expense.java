package com.example.vaishnavirachapudi.myexpenseapp;

import java.io.Serializable;

/**
 * Created by vaishnavirachapudi on 02/24/16.
 */
public class Expense implements Serializable{
    private String category, date, name, user;
    private String amount;

    public Expense() {
    }

    public Expense(String category, String date, String name, String user, String amount) {
        this.category = category;
        this.date = date;
        this.name = name;
        this.user = user;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", amount=" + amount +
                '}';
    }
}
