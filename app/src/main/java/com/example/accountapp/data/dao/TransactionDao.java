package com.example.accountapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.accountapp.data.entity.TransactionEntity;
import java.util.Date;
import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Query("SELECT * FROM transactions WHERE id = :id")
    LiveData<TransactionEntity> getTransactionById(long id);

    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getTransactionsByAccount(long accountId);

    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getTransactionsByCategory(long categoryId);

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getTransactionsByType(TransactionEntity.Type type);

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getTransactionsByDateRange(Date startDate, Date endDate);

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND date BETWEEN :startDate AND :endDate")
    LiveData<Double> getTotalByTypeAndDateRange(TransactionEntity.Type type, Date startDate, Date endDate);

    @Query("SELECT SUM(amount) FROM transactions WHERE accountId = :accountId AND type = :type AND date BETWEEN :startDate AND :endDate")
    LiveData<Double> getTotalByAccountAndTypeAndDateRange(long accountId, TransactionEntity.Type type, Date startDate, Date endDate);

    @Insert
    void insert(TransactionEntity transaction);

    @Update
    void update(TransactionEntity transaction);

    @Delete
    void delete(TransactionEntity transaction);
} 