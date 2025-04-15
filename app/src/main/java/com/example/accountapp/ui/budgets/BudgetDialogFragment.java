package com.example.accountapp.ui.budgets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Budget;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.databinding.DialogBudgetBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.List;

public class BudgetDialogFragment extends DialogFragment {
    private DialogBudgetBinding binding;
    private BudgetsViewModel viewModel;
    private Budget budget;
    private List<Category> categories;

    public static BudgetDialogFragment newInstance(Budget budget) {
        BudgetDialogFragment fragment = new BudgetDialogFragment();
        if (budget != null) {
            Bundle args = new Bundle();
            args.putLong("budget_id", budget.getId());
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment()).get(BudgetsViewModel.class);
        if (getArguments() != null) {
            long budgetId = getArguments().getLong("budget_id");
            // TODO: Load budget by ID
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogBudgetBinding.inflate(LayoutInflater.from(getContext()));
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(budget == null ? R.string.add_budget : R.string.edit_budget)
                .setView(binding.getRoot())
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dismiss())
                .create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        observeData();
    }

    private void setupViews() {
        // TODO: Setup category spinner
        // TODO: Setup amount input
        // TODO: Setup month/year selection
    }

    private void observeData() {
        // TODO: Observe categories
        // TODO: Observe budget if editing
    }

    private void updateCategorySpinner(List<Category> categories) {
        this.categories = categories;
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, categories);
        binding.spinnerCategory.setAdapter(adapter);
    }

    private void populateFields(Budget budget) {
        this.budget = budget;
        // TODO: Populate fields with budget data
    }

    private void saveBudget() {
        String name = binding.editName.getText().toString();
        String amountText = binding.editAmount.getText().toString();
        
        if (name.isEmpty() || amountText.isEmpty() || 
            binding.spinnerCategory.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);
        Category selectedCategory = (Category) binding.spinnerCategory.getAdapter().getItem(
            binding.spinnerCategory.getListSelection());

        Budget budget = new Budget(selectedCategory.getId(), amount, viewModel.getCurrentMonth(), viewModel.getCurrentYear());
        budget.setCategoryName(selectedCategory.getName());

        if (this.budget == null) {
            viewModel.insert(budget);
        } else {
            budget.setId(this.budget.getId());
            viewModel.update(budget);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 