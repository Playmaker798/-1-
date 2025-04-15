package com.example.accountapp.ui.budgets;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.entity.Budget;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.repository.BudgetRepository;
import com.example.accountapp.data.repository.CategoryRepository;
import java.util.Calendar;
import java.util.List;

public class BudgetsViewModel extends AndroidViewModel {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private int currentMonth;
    private int currentYear;

    public BudgetsViewModel(Application application) {
        super(application);
        budgetRepository = new BudgetRepository(application);
        categoryRepository = new CategoryRepository(application);
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
    }

    public LiveData<List<Budget>> getBudgets() {
        return budgetRepository.getBudgetsByMonthAndYear(currentMonth, currentYear);
    }

    public LiveData<List<Category>> getCategories() {
        return categoryRepository.getCategories();
    }

    public void insert(Budget budget) {
        budgetRepository.insert(budget);
    }

    public void update(Budget budget) {
        budgetRepository.update(budget);
    }

    public void delete(Budget budget) {
        budgetRepository.delete(budget);
    }

    public LiveData<Budget> getBudget(long id) {
        return budgetRepository.getBudgetById(id);
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setMonthAndYear(int month, int year) {
        currentMonth = month;
        currentYear = year;
    }

    public LiveData<List<Budget>> getBudgetsByMonthAndYear(int month, int year) {
        return budgetRepository.getBudgetsByMonthAndYear(month, year);
    }
} 