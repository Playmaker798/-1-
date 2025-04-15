package com.example.accountapp.ui.budgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Budget;
import com.example.accountapp.databinding.FragmentBudgetsBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BudgetsFragment extends Fragment {
    private FragmentBudgetsBinding binding;
    private BudgetsViewModel viewModel;
    private BudgetAdapter adapter;
    private Calendar selectedMonth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BudgetsViewModel.class);
        adapter = new BudgetAdapter(budget -> showDeleteConfirmationDialog(budget));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBudgetsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedMonth = Calendar.getInstance();
        setupViews();
        observeData();
    }

    private void setupViews() {
        binding.recyclerViewBudgets.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewBudgets.setAdapter(adapter);

        binding.fabAddBudget.setOnClickListener(v -> showAddBudgetDialog());

        binding.buttonPreviousMonth.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(viewModel.getCurrentYear(), viewModel.getCurrentMonth() - 1, 1);
            viewModel.setMonthAndYear(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            updateMonthText();
        });

        binding.buttonNextMonth.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(viewModel.getCurrentYear(), viewModel.getCurrentMonth() + 1, 1);
            viewModel.setMonthAndYear(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            updateMonthText();
        });

        updateMonthText();
    }

    private void updateMonthText() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(viewModel.getCurrentYear(), viewModel.getCurrentMonth(), 1);
        binding.textViewMonth.setText(String.format("%tB %tY", calendar, calendar));
    }

    private void observeData() {
        viewModel.getBudgets().observe(getViewLifecycleOwner(), budgets -> {
            adapter.submitList(budgets);
            binding.textViewNoBudgets.setVisibility(budgets.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void showDeleteConfirmationDialog(Budget budget) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.delete_budget)
                .setMessage(R.string.delete_budget_confirmation)
                .setPositiveButton(R.string.delete, (dialog, which) -> viewModel.delete(budget))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void showAddBudgetDialog() {
        BudgetDialogFragment dialog = BudgetDialogFragment.newInstance(null);
        dialog.show(getChildFragmentManager(), "add_budget");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 