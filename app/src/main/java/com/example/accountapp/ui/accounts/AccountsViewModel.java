package com.example.accountapp.ui.accounts;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.repository.AccountRepository;
import java.util.List;

public class AccountsViewModel extends AndroidViewModel {
    private final AccountRepository repository;

    public AccountsViewModel(Application application) {
        super(application);
        repository = new AccountRepository(application);
    }

    public LiveData<List<Account>> getAllAccounts() {
        return repository.getAllAccounts();
    }

    public LiveData<Account> getAccount(long id) {
        return repository.getAccount(id);
    }

    public void insert(Account account) {
        repository.insert(account);
    }

    public void update(Account account) {
        repository.update(account);
    }

    public void deleteAccount(Account account) {
        repository.delete(account);
    }
} 