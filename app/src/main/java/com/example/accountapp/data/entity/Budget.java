package com.example.accountapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import com.example.accountapp.data.TransactionType;

@Entity(
    tableName = "budgets",
    foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "categoryId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("categoryId")}
)
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long categoryId;
    private String categoryName;
    private double amount;
    private double spent;
    private int month;
    private int year;
    private TransactionType type;

    public Budget() {
    }

    @Ignore
    public Budget(long categoryId, double amount, int month, int year) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.spent = 0;
    }

    @Ignore
    public Budget(long categoryId, double amount, int month, int year, TransactionType type) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.spent = 0;
        this.month = month;
        this.year = year;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return id == budget.id &&
            categoryId == budget.categoryId &&
            Double.compare(budget.amount, amount) == 0 &&
            Double.compare(budget.spent, spent) == 0 &&
            month == budget.month &&
            year == budget.year &&
            categoryName.equals(budget.categoryName);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, categoryId, categoryName, amount, spent, month, year);
    }
} 