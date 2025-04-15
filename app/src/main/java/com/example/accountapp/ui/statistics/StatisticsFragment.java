package com.example.accountapp.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.accountapp.R;
import com.example.accountapp.databinding.FragmentStatisticsBinding;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatisticsFragment extends Fragment {
    private FragmentStatisticsBinding binding;
    private StatisticsViewModel viewModel;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        observeData();
    }

    private void setupViews() {
        binding.buttonPreviousMonth.setOnClickListener(v -> {
            Calendar cal = viewModel.getSelectedDate();
            cal.add(Calendar.MONTH, -1);
            viewModel.setSelectedDate(cal);
            updateDateDisplay();
        });

        binding.buttonNextMonth.setOnClickListener(v -> {
            Calendar cal = viewModel.getSelectedDate();
            cal.add(Calendar.MONTH, 1);
            viewModel.setSelectedDate(cal);
            updateDateDisplay();
        });
    }

    private void observeData() {
        viewModel.getTransactions().observe(getViewLifecycleOwner(), transactions -> {
            updateStatistics();
        });
    }

    private void updateDateDisplay() {
        Calendar cal = viewModel.getSelectedDate();
        String monthYear = String.format(Locale.getDefault(), "%tB %tY", cal, cal);
        binding.textViewMonthYear.setText(monthYear);
        updateStatistics();
    }

    private void updateStatistics() {
        double income = viewModel.getTotalIncome();
        double expense = viewModel.getTotalExpense();
        double balance = viewModel.getBalance();

        binding.textViewIncome.setText(currencyFormat.format(income));
        binding.textViewExpense.setText(currencyFormat.format(expense));
        binding.textViewBalance.setText(currencyFormat.format(balance));

        // Update progress bars
        double total = income + expense;
        if (total > 0) {
            int incomePercentage = (int) ((income / total) * 100);
            int expensePercentage = (int) ((expense / total) * 100);
            binding.progressBarIncome.setProgress(incomePercentage);
            binding.progressBarExpense.setProgress(expensePercentage);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 