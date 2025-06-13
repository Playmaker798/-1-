package com.example.accountapp.ui.statistics;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.MediatorLiveData;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.data.repository.TransactionRepository;
import com.example.accountapp.data.repository.CategoryRepository;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.repository.AccountRepository;
import com.example.accountapp.data.entity.Category;

public class StatisticsViewModel extends AndroidViewModel {
    private final TransactionRepository repository;
    private final CategoryRepository categoryRepository;
    private final MediatorLiveData<List<TransactionEntity>> filteredTransactions = new MediatorLiveData<>();
    private LiveData<List<TransactionEntity>> currentSource;
    private Calendar selectedDate;
    private Date filterStartDate = null;
    private Date filterEndDate = null;
    private String selectedAccountName = null;
    private TransactionEntity.Type selectedType = null;
    private Long selectedAccountId = null;
    private List<TransactionEntity> cachedTransactions = null;
    private Long selectedCategoryId = null;

    public StatisticsViewModel(Application application) {
        super(application);
        repository = new TransactionRepository(application);
        categoryRepository = new CategoryRepository(application);
        selectedDate = Calendar.getInstance();
        // 默认加载全部
        switchSource();
    }

    public LiveData<List<TransactionEntity>> getTransactions() {
        return filteredTransactions;
    }

    private void switchSource() {
        LiveData<List<TransactionEntity>> newSource;
        if (filterStartDate != null && filterEndDate != null) {
            android.util.Log.d("StatisticsViewModel", "Filtering transactions with: " +
                    "accountId=" + selectedAccountId + ", " +
                    "type=" + selectedType + ", " +
                    "categoryId=" + selectedCategoryId + ", " +
                    "startDate=" + filterStartDate + ", " +
                    "endDate=" + filterEndDate);
            if (selectedCategoryId != null) {
                newSource = repository.getTransactionsByCategory(selectedCategoryId);
            } else {
                newSource = repository.getTransactionsByAccountTypeDateRange(selectedAccountId, selectedType, filterStartDate, filterEndDate);
            }
        } else {
            android.util.Log.d("StatisticsViewModel", "Loading all transactions");
            newSource = repository.getAllTransactions();
        }
        if (currentSource != null) {
            filteredTransactions.removeSource(currentSource);
        }
        currentSource = newSource;
        filteredTransactions.addSource(currentSource, list -> {
            if (list != null && selectedCategoryId != null) {
                list = list.stream()
                    .filter(t -> t.getCategoryId() == selectedCategoryId)
                    .toList();
            }
            cachedTransactions = list;
            android.util.Log.d("StatisticsViewModel", "Received " + (list != null ? list.size() : 0) + " transactions");
            if (list != null) {
                double income = getTotalIncome();
                double expense = getTotalExpense();
                android.util.Log.d("StatisticsViewModel", "Calculated totals - Income: " + income + ", Expense: " + expense);
            }
            filteredTransactions.setValue(list);
        });
    }

    public void setSelectedDate(Calendar date) {
        this.selectedDate = date;
    }

    public Calendar getSelectedDate() {
        return selectedDate;
    }

    public void setDateRange(Calendar start, Calendar end) {
        // 设置开始日期为当天的开始（00:00:00）
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        
        // 设置结束日期为当天的结束（23:59:59）
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);
        
        this.filterStartDate = start.getTime();
        this.filterEndDate = end.getTime();
        switchSource();
    }

    public double getTotalIncome() {
        if (cachedTransactions == null) return 0.0;
        double total = cachedTransactions.stream()
                .filter(t -> t.getType() == TransactionEntity.Type.INCOME)
                .mapToDouble(TransactionEntity::getAmount)
                .sum();
        android.util.Log.d("StatisticsViewModel", "Calculating total income: " + total + " from " + cachedTransactions.size() + " transactions");
        return total;
    }

    public double getTotalExpense() {
        if (cachedTransactions == null) return 0.0;
        double total = cachedTransactions.stream()
                .filter(t -> t.getType() == TransactionEntity.Type.EXPENSE)
                .mapToDouble(TransactionEntity::getAmount)
                .sum();
        // 确保支出金额为正数
        total = Math.abs(total);
        android.util.Log.d("StatisticsViewModel", "Calculating total expense: " + total + " from " + cachedTransactions.size() + " transactions");
        return total;
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpense();
    }

    public void setAccountId(Long id) {
        this.selectedAccountId = id;
        switchSource();
    }

    public void setType(String type) {
        if (type == null || "全部".equals(type)) {
            this.selectedType = null;
        } else if ("收入".equals(type)) {
            this.selectedType = TransactionEntity.Type.INCOME;
        } else if ("支出".equals(type)) {
            this.selectedType = TransactionEntity.Type.EXPENSE;
        } else {
            this.selectedType = null;
        }
        switchSource();
    }

    public void setCategory(Category category) {
        this.selectedCategoryId = category == null ? null : category.getId();
        switchSource();
    }
} 