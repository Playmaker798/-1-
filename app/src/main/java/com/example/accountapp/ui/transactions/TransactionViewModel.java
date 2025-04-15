package com.example.accountapp.ui.transactions;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.data.repository.TransactionRepository;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private final TransactionRepository repository;
    private final LiveData<List<TransactionEntity>> allTransactions;

    public TransactionViewModel(Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransactions = repository.getAllTransactions();
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return allTransactions;
    }

    public void insert(TransactionEntity transaction) {
        repository.insert(transaction);
    }

    public void update(TransactionEntity transaction) {
        repository.update(transaction);
    }

    public void delete(TransactionEntity transaction) {
        repository.delete(transaction);
    }
} 