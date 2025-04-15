package com.example.accountapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.example.accountapp.data.TransactionType;
import java.util.Date;

@Entity(tableName = "transactions",
        foreignKeys = {
            @ForeignKey(entity = Account.class,
                    parentColumns = "id",
                    childColumns = "accountId",
                    onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = Category.class,
                    parentColumns = "id",
                    childColumns = "categoryId",
                    onDelete = ForeignKey.CASCADE)
        },
        indices = {
            @Index("accountId"),
            @Index("categoryId")
        })
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private double amount;
    private TransactionType type;
    private long accountId;
    private long categoryId;
    private Date date;
    private String note;

    public Transaction(double amount, TransactionType type, long accountId, long categoryId, Date date, String note) {
        this.amount = amount;
        this.type = type;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.date = date;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
} 