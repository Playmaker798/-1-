package com.example.accountapp.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.data.repository.TransactionRepository;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final TransactionRepository repository;

    public HomeViewModel(Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }

    public LiveData<List<TransactionEntity>> getRecentTransactions() {
        return repository.getAllTransactions();
    }
} 