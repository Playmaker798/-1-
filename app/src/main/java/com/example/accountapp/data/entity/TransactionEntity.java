package com.example.accountapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;
import com.example.accountapp.data.Converters;
import com.example.accountapp.data.TransactionType;

import java.util.Date;

@Entity(
    tableName = "transactions",
    foreignKeys = {
        @ForeignKey(
            entity = Account.class,
            parentColumns = "id",
            childColumns = "accountId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Category.class,
            parentColumns = "id",
            childColumns = "categoryId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("accountId"),
        @Index("categoryId")
    }
)
public class TransactionEntity {
    public enum Type {
        INCOME,
        EXPENSE
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Type type;
    private long accountId;
    private long categoryId;
    private double amount;
    private String description;
    @TypeConverters(Converters.class)
    private Date date;
    private String note;

    public TransactionEntity() {
    }

    @Ignore
    public TransactionEntity(long accountId, long categoryId, double amount, String description, Date date) {
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Ignore
    public TransactionEntity(long accountId, long categoryId, double amount, String description, Date date, Type type) {
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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