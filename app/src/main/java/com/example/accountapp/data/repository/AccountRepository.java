package com.example.accountapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.AppDatabase;
import com.example.accountapp.data.dao.AccountDao;
import com.example.accountapp.data.entity.Account;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountRepository {
    private final AccountDao accountDao;
    private final ExecutorService executorService;

    public AccountRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        accountDao = db.accountDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    public LiveData<Account> getAccount(long id) {
        return accountDao.getAccountById(id);
    }

    public void insert(Account account) {
        executorService.execute(() -> accountDao.insert(account));
    }

    public void update(Account account) {
        executorService.execute(() -> accountDao.update(account));
    }

    public void delete(Account account) {
        executorService.execute(() -> accountDao.delete(account));
    }

    public void updateBalance(long accountId, double amount) {
        executorService.execute(() -> accountDao.updateBalance(accountId, amount));
    }
} 