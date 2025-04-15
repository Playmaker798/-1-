package com.example.accountapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.AppDatabase;
import com.example.accountapp.data.dao.BudgetDao;
import com.example.accountapp.data.entity.Budget;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BudgetRepository {
    private final BudgetDao budgetDao;
    private final ExecutorService executorService;

    public BudgetRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        budgetDao = db.budgetDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Budget>> getBudgetsByMonthAndYear(int month, int year) {
        return budgetDao.getBudgetsByMonthAndYear(month, year);
    }

    public LiveData<Budget> getBudgetById(long id) {
        return budgetDao.getBudgetById(id);
    }

    public void insert(Budget budget) {
        executorService.execute(() -> budgetDao.insert(budget));
    }

    public void update(Budget budget) {
        executorService.execute(() -> budgetDao.update(budget));
    }

    public void delete(Budget budget) {
        executorService.execute(() -> budgetDao.delete(budget));
    }

    public void updateSpent(long id, double spent) {
        executorService.execute(() -> budgetDao.updateSpent(id, spent));
    }
} 