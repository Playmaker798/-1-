package com.example.accountapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.accountapp.data.entity.Account;
import java.util.List;

@Dao
public interface AccountDao {
    @Query("SELECT * FROM accounts ORDER BY name")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT * FROM accounts WHERE id = :id")
    LiveData<Account> getAccountById(long id);

    @Insert
    void insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Query("UPDATE accounts SET balance = balance + :amount WHERE id = :accountId")
    void updateBalance(long accountId, double amount);
} 