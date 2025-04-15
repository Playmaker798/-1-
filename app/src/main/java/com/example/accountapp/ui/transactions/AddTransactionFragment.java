package com.example.accountapp.ui.transactions;

import android.app.DatePickerDialog;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTransactionFragment extends DialogFragment {
    private DialogTransactionBinding binding;
    private TransactionsViewModel viewModel;
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private ArrayAdapter<Account> accountAdapter;
    private ArrayAdapter<Category> categoryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AccountApp_Dialog);
        viewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
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
    }

    private void setupViews() {
        binding.buttonCancel.setOnClickListener(v -> dismiss());
        binding.buttonSave.setOnClickListener(v -> saveTransaction());
        binding.editTextDate.setOnClickListener(v -> showDatePicker());
        
        // 设置交易类型选项
        String[] types = {getString(R.string.income), getString(R.string.expense)};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            types
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerType.setAdapter(typeAdapter);
        
        // 设置初始日期
        binding.editTextDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void observeData() {
        viewModel.getAccounts().observe(getViewLifecycleOwner(), accounts -> {
            accountAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                accounts
            );
            accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerAccount.setAdapter(accountAdapter);
        });

        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            categoryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
            );
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerCategory.setAdapter(categoryAdapter);
        });
    }

    private void saveTransaction() {
        String amountText = binding.editTextAmount.getText().toString();
        String description = binding.editTextDescription.getText().toString();
        String note = binding.editTextNote.getText().toString();
        
        // 获取选中的账户
        String accountName = binding.spinnerAccount.getText().toString();
        Account account = null;
        for (int i = 0; i < accountAdapter.getCount(); i++) {
            Account acc = accountAdapter.getItem(i);
            if (acc.getName().equals(accountName)) {
                account = acc;
                break;
            }
        }
        
        // 获取选中的分类
        String categoryName = binding.spinnerCategory.getText().toString();
        Category category = null;
        for (int i = 0; i < categoryAdapter.getCount(); i++) {
            Category cat = categoryAdapter.getItem(i);
            if (cat.getName().equals(categoryName)) {
                category = cat;
                break;
            }
        }
        
        String type = binding.spinnerType.getText().toString();

        if (amountText.isEmpty() || description.isEmpty() || account == null || category == null || type.isEmpty()) {
            Toast.makeText(requireContext(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);
        if (type.equals(getString(R.string.expense))) {
            amount = -amount;
        }

        TransactionEntity transaction = new TransactionEntity(
            account.getId(),
            category.getId(),
            amount,
            description,
            calendar.getTime()
        );
        transaction.setNote(note);
        transaction.setType(type.equals(getString(R.string.income)) ? 
            TransactionEntity.Type.INCOME : TransactionEntity.Type.EXPENSE);
        
        viewModel.insert(transaction);
        dismiss();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            requireContext(),
            (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                binding.editTextDate.setText(dateFormat.format(calendar.getTime()));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 