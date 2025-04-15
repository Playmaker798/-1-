package com.example.accountapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.accountapp.data.entity.Budget;
import java.util.List;

@Dao
public interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE month = :month AND year = :year ORDER BY categoryName")
    LiveData<List<Budget>> getBudgetsByMonthAndYear(int month, int year);

    @Query("SELECT * FROM budgets WHERE id = :id")
    LiveData<Budget> getBudgetById(long id);

    @Insert
    void insert(Budget budget);

    @Update
    void update(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("UPDATE budgets SET spent = :spent WHERE id = :id")
    void updateSpent(long id, double spent);
} 