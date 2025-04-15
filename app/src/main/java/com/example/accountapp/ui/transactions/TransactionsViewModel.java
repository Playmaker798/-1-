package com.example.accountapp.ui.transactions;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.data.repository.AccountRepository;
import com.example.accountapp.data.repository.CategoryRepository;
import com.example.accountapp.data.repository.TransactionRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionsViewModel extends AndroidViewModel {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private LiveData<List<TransactionEntity>> transactions;
    private final ExecutorService executorService;

    public TransactionsViewModel(Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        accountRepository = new AccountRepository(application);
        categoryRepository = new CategoryRepository(application);
        transactions = transactionRepository.getAllTransactions();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<TransactionEntity>> getTransactions() {
        return transactions;
    }

    public LiveData<List<Account>> getAccounts() {
        return accountRepository.getAllAccounts();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryRepository.getCategories();
    }

    public LiveData<List<Category>> getCategoriesByType(TransactionEntity.Type type) {
        return categoryRepository.getCategoriesByType(type);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByAccount(long accountId) {
        return transactionRepository.getTransactionsByAccount(accountId);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByCategory(long categoryId) {
        return transactionRepository.getTransactionsByCategory(categoryId);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByType(TransactionEntity.Type type) {
        return transactionRepository.getTransactionsByType(type);
    }

    public LiveData<List<TransactionEntity>> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.getTransactionsByDateRange(startDate, endDate);
    }

    public LiveData<Double> getTotalByTypeAndDateRange(TransactionEntity.Type type, Date startDate, Date endDate) {
        return transactionRepository.getTotalByTypeAndDateRange(type, startDate, endDate);
    }

    public LiveData<Double> getTotalByAccountAndTypeAndDateRange(long accountId, TransactionEntity.Type type, Date startDate, Date endDate) {
        return transactionRepository.getTotalByAccountAndTypeAndDateRange(accountId, type, startDate, endDate);
    }

    public void insert(TransactionEntity transaction) {
        transactionRepository.insert(transaction);
    }

    public void update(TransactionEntity transaction) {
        transactionRepository.update(transaction);
    }

    public void delete(TransactionEntity transaction) {
        transactionRepository.delete(transaction);
    }

    public LiveData<TransactionEntity> getTransaction(long id) {
        return transactionRepository.getTransactionById(id);
    }

    public Date getStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Date getEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
} 