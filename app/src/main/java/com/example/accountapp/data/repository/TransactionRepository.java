package com.example.accountapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.AppDatabase;
import com.example.accountapp.data.dao.TransactionDao;
import com.example.accountapp.data.entity.TransactionEntity;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepository {
    private final TransactionDao transactionDao;
    private final ExecutorService executorService;

    public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        transactionDao = db.transactionDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    public LiveData<TransactionEntity> getTransactionById(long id) {
        return transactionDao.getTransactionById(id);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByAccount(long accountId) {
        return transactionDao.getTransactionsByAccount(accountId);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByCategory(long categoryId) {
        return transactionDao.getTransactionsByCategory(categoryId);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByType(TransactionEntity.Type type) {
        return transactionDao.getTransactionsByType(type);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionDao.getTransactionsByDateRange(startDate, endDate);
    }

    public LiveData<Double> getTotalByTypeAndDateRange(TransactionEntity.Type type, Date startDate, Date endDate) {
        return transactionDao.getTotalByTypeAndDateRange(type, startDate, endDate);
    }

    public LiveData<Double> getTotalByAccountAndTypeAndDateRange(long accountId, TransactionEntity.Type type, Date startDate, Date endDate) {
        return transactionDao.getTotalByAccountAndTypeAndDateRange(accountId, type, startDate, endDate);
    }

    public void insert(TransactionEntity transaction) {
        executorService.execute(() -> transactionDao.insert(transaction));
    }

    public void update(TransactionEntity transaction) {
        executorService.execute(() -> transactionDao.update(transaction));
    }

    public void delete(TransactionEntity transaction) {
        executorService.execute(() -> transactionDao.delete(transaction));
    }
} 