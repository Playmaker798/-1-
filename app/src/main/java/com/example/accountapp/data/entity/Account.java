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

    public enum AccountType {
        CASH,    // 现金
        BANK,    // 银行卡
        CREDIT,  // 信用卡
        ALIPAY,  // 支付宝
        WECHAT,  // 微信
        OTHER;   // 其他

        public String getDisplayName() {
            switch (this) {
                case CASH: return "现金";
                case BANK: return "储蓄卡";
                case CREDIT: return "信用卡";
                case ALIPAY: return "支付宝";
                case WECHAT: return "微信";
                case OTHER: return "其他";
                default: return this.name();
            }
        }
    }

    // 无参构造函数
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

    @Override
    public String toString() {
        return name;
    }
} 