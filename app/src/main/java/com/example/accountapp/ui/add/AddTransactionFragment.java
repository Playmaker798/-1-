package com.example.accountapp.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.data.TransactionType;
import com.example.accountapp.databinding.FragmentAddTransactionBinding;
import com.example.accountapp.ui.transactions.TransactionsViewModel;
import java.util.Date;

public class AddTransactionFragment extends DialogFragment {
    private FragmentAddTransactionBinding binding;
    private TransactionsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AccountApp_Dialog);
        viewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        observeData();
    }

    private void setupViews() {
        binding.buttonCancel.setOnClickListener(v -> dismiss());
        binding.buttonSave.setOnClickListener(v -> saveTransaction());
        binding.buttonDate.setOnClickListener(v -> showDatePicker());
    }

    private void observeData() {
        viewModel.getAccounts().observe(getViewLifecycleOwner(), accounts -> {
            ArrayAdapter<Account> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                accounts
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerAccount.setAdapter(adapter);
        });

        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerCategory.setAdapter(adapter);
        });
    }

    private void saveTransaction() {
        String amountText = binding.editTextAmount.getText().toString();
        String description = binding.editTextDescription.getText().toString();
        Account account = (Account) binding.spinnerAccount.getSelectedItem();
        Category category = (Category) binding.spinnerCategory.getSelectedItem();

        if (amountText.isEmpty() || description.isEmpty() || account == null || category == null) {
            Toast.makeText(requireContext(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);
        TransactionEntity transaction = new TransactionEntity(
            account.getId(),
            category.getId(),
            amount,
            description,
            new Date()
        );
        viewModel.insert(transaction);
        dismiss();
    }

    private void showDatePicker() {
        // TODO: Implement date picker
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 