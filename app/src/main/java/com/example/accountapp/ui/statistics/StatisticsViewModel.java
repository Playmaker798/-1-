package com.example.accountapp.ui.statistics;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.data.repository.TransactionRepository;
import java.util.List;
import java.util.Calendar;

public class StatisticsViewModel extends AndroidViewModel {
    private final TransactionRepository repository;
    private final LiveData<List<TransactionEntity>> transactions;
    private Calendar selectedDate;

    public StatisticsViewModel(Application application) {
        super(application);
        repository = new TransactionRepository(application);
        selectedDate = Calendar.getInstance();
        transactions = repository.getAllTransactions();
    }

    public LiveData<List<TransactionEntity>> getTransactions() {
        return transactions;
    }

    public void setSelectedDate(Calendar date) {
        this.selectedDate = date;
    }

    public Calendar getSelectedDate() {
        return selectedDate;
    }

    public double getTotalIncome() {
        List<TransactionEntity> transactionList = transactions.getValue();
        if (transactionList == null) return 0.0;

        return transactionList.stream()
                .filter(t -> t.getType() == TransactionEntity.Type.INCOME)
                .mapToDouble(TransactionEntity::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        List<TransactionEntity> transactionList = transactions.getValue();
        if (transactionList == null) return 0.0;

        return transactionList.stream()
                .filter(t -> t.getType() == TransactionEntity.Type.EXPENSE)
                .mapToDouble(TransactionEntity::getAmount)
                .sum();
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpense();
    }
} 