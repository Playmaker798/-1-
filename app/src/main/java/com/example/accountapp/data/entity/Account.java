package com.example.accountapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "accounts")
public class Account {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private AccountType type;
    private double balance;
    private String note;

    public Account() {
    }

    @Ignore
    public Account(String name, AccountType type, double balance, String note) {
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.note = note;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
} 