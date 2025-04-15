package com.example.accountapp.ui.transactions;

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
import com.example.accountapp.databinding.DialogTransactionBinding;
import java.util.Calendar;
import java.util.Date;

public class TransactionDialogFragment extends DialogFragment {
    private DialogTransactionBinding binding;
    private TransactionsViewModel viewModel;
    private TransactionEntity transaction;
    private Calendar selectedDate;

    public static TransactionDialogFragment newInstance(long transactionId) {
        TransactionDialogFragment fragment = new TransactionDialogFragment();
        Bundle args = new Bundle();
        args.putLong("transactionId", transactionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AccountApp_Dialog);
        viewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
        selectedDate = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        observeData();
        if (getArguments() != null) {
            long transactionId = getArguments().getLong("transactionId", -1);
            if (transactionId != -1) {
                viewModel.getTransaction(transactionId).observe(getViewLifecycleOwner(), transaction -> {
                    this.transaction = transaction;
                    populateFields();
                });
            }
        }
    }

    private void setupViews() {
        binding.buttonCancel.setOnClickListener(v -> dismiss());
        binding.buttonSave.setOnClickListener(v -> saveTransaction());
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

    private void populateFields() {
        if (transaction != null) {
            binding.editTextDescription.setText(transaction.getDescription());
            binding.editTextAmount.setText(String.valueOf(Math.abs(transaction.getAmount())));
            binding.editTextNote.setText(transaction.getNote());
            
            // Set account in spinner
            for (int i = 0; i < binding.spinnerAccount.getAdapter().getCount(); i++) {
                Account account = (Account) binding.spinnerAccount.getAdapter().getItem(i);
                if (account.getId() == transaction.getAccountId()) {
                    binding.spinnerAccount.setText(account.getName(), false);
                    break;
                }
            }
            
            // Set category in spinner
            for (int i = 0; i < binding.spinnerCategory.getAdapter().getCount(); i++) {
                Category category = (Category) binding.spinnerCategory.getAdapter().getItem(i);
                if (category.getId() == transaction.getCategoryId()) {
                    binding.spinnerCategory.setText(category.getName(), false);
                    break;
                }
            }
            
            // Set type
            String type = transaction.getType() == TransactionEntity.Type.INCOME ? 
                getString(R.string.income) : getString(R.string.expense);
            binding.spinnerType.setText(type, false);
            
            // Set date
            selectedDate.setTime(transaction.getDate());
            binding.editTextDate.setText(transaction.getDate().toString());
        }
    }

    private void saveTransaction() {
        String description = binding.editTextDescription.getText().toString();
        String amountText = binding.editTextAmount.getText().toString();
        String note = binding.editTextNote.getText().toString();
        String type = binding.spinnerType.getText().toString();
        
        // Find selected account
        Account account = null;
        for (int i = 0; i < binding.spinnerAccount.getAdapter().getCount(); i++) {
            Account a = (Account) binding.spinnerAccount.getAdapter().getItem(i);
            if (a.getName().equals(binding.spinnerAccount.getText().toString())) {
                account = a;
                break;
            }
        }

        // Find selected category
        Category category = null;
        for (int i = 0; i < binding.spinnerCategory.getAdapter().getCount(); i++) {
            Category c = (Category) binding.spinnerCategory.getAdapter().getItem(i);
            if (c.getName().equals(binding.spinnerCategory.getText().toString())) {
                category = c;
                break;
            }
        }

        if (description.isEmpty() || amountText.isEmpty() || account == null || category == null || type.isEmpty()) {
            Toast.makeText(requireContext(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);
        if (type.equals(getString(R.string.expense))) {
            amount = -amount;
        }

        if (transaction == null) {
            transaction = new TransactionEntity(
                account.getId(),
                category.getId(),
                amount,
                description,
                selectedDate.getTime()
            );
            transaction.setNote(note);
            transaction.setType(type.equals(getString(R.string.income)) ? 
                TransactionEntity.Type.INCOME : TransactionEntity.Type.EXPENSE);
            viewModel.insert(transaction);
        } else {
            transaction.setAccountId(account.getId());
            transaction.setCategoryId(category.getId());
            transaction.setAmount(amount);
            transaction.setDescription(description);
            transaction.setNote(note);
            transaction.setDate(selectedDate.getTime());
            transaction.setType(type.equals(getString(R.string.income)) ? 
                TransactionEntity.Type.INCOME : TransactionEntity.Type.EXPENSE);
            viewModel.update(transaction);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 