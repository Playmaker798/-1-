package com.example.accountapp.ui.accounts;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.entity.AccountType;
import com.example.accountapp.databinding.DialogAccountBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AccountDialogFragment extends DialogFragment {
    private DialogAccountBinding binding;
    private AccountsViewModel viewModel;
    private Account account;
    private long accountId = -1;

    public static AccountDialogFragment newInstance() {
        return new AccountDialogFragment();
    }

    public static AccountDialogFragment newInstance(long id) {
        AccountDialogFragment fragment = new AccountDialogFragment();
        Bundle args = new Bundle();
        args.putLong("accountId", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AccountsViewModel.class);
        if (getArguments() != null) {
            accountId = getArguments().getLong("accountId", -1);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogAccountBinding.inflate(getLayoutInflater());
        
        if (accountId != -1) {
            viewModel.getAccount(accountId).observe(this, account -> {
                if (account != null) {
                    this.account = account;
                    populateFields();
                }
            });
        }

        setupViews();
        return new MaterialAlertDialogBuilder(requireContext())
                .setView(binding.getRoot())
                .create();
    }

    private void setupViews() {
        ArrayAdapter<AccountType> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                AccountType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAccountType.setAdapter(adapter);

        binding.buttonSave.setOnClickListener(v -> saveAccount());
        binding.buttonCancel.setOnClickListener(v -> dismiss());
    }

    private void populateFields() {
        if (account != null) {
            binding.editTextName.setText(account.getName());
            binding.spinnerAccountType.setSelection(account.getType().ordinal());
            binding.editTextBalance.setText(String.valueOf(account.getBalance()));
            binding.editTextNote.setText(account.getNote());
        }
    }

    private void saveAccount() {
        String name = binding.editTextName.getText().toString();
        AccountType type = (AccountType) binding.spinnerAccountType.getSelectedItem();
        double balance = Double.parseDouble(binding.editTextBalance.getText().toString());
        String note = binding.editTextNote.getText().toString();

        if (account == null) {
            account = new Account(name, type, balance, note);
            viewModel.insert(account);
        } else {
            account.setName(name);
            account.setType(type);
            account.setBalance(balance);
            account.setNote(note);
            viewModel.update(account);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 