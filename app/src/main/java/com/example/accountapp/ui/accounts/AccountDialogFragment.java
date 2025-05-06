package com.example.accountapp.ui.accounts;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Account;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Arrays;

public class AccountDialogFragment extends DialogFragment {
    private TextInputEditText nameInput;
    private Spinner typeSpinner;
    private TextInputEditText balanceInput;
    private TextInputEditText noteInput;
    private Button saveButton;
    private Button cancelButton;
    private Account account;
    private OnAccountSaveListener listener;
    private AccountsViewModel viewModel;
    private long accountId = -1;

    public interface OnAccountSaveListener {
        void onAccountSave(Account account);
    }

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

    public void setOnAccountSaveListener(OnAccountSaveListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AccountsViewModel.class);
        if (getArguments() != null) {
            accountId = getArguments().getLong("accountId", -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_account, container, false);
        initViews(view);
        setupSpinner();
        setupButtons();
        
        if (accountId != -1) {
            viewModel.getAccount(accountId).observe(this, account -> {
                if (account != null) {
                    this.account = account;
                    populateFields();
                }
            });
        }
        
        return view;
    }

    private void initViews(View view) {
        nameInput = view.findViewById(R.id.account_name_input);
        typeSpinner = view.findViewById(R.id.account_type_spinner);
        balanceInput = view.findViewById(R.id.account_balance_input);
        noteInput = view.findViewById(R.id.account_note_input);
        saveButton = view.findViewById(R.id.save_button);
        cancelButton = view.findViewById(R.id.cancel_button);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Arrays.stream(Account.AccountType.values())
                  .map(Account.AccountType::getDisplayName)
                  .toArray(String[]::new)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private void setupButtons() {
        saveButton.setOnClickListener(v -> saveAccount());
        cancelButton.setOnClickListener(v -> dismiss());
    }

    private void saveAccount() {
        String name = nameInput.getText().toString().trim();
        int typeIndex = typeSpinner.getSelectedItemPosition();
        Account.AccountType type = Account.AccountType.values()[typeIndex];
        String balanceStr = balanceInput.getText().toString().trim();
        String note = noteInput.getText().toString().trim();

        if (name.isEmpty() || balanceStr.isEmpty()) {
            // 显示错误提示
            return;
        }

        double balance = Double.parseDouble(balanceStr);

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

        if (listener != null) {
            listener.onAccountSave(account);
        }
        dismiss();
    }

    private void populateFields() {
        if (account != null) {
            nameInput.setText(account.getName());
            typeSpinner.setSelection(account.getType().ordinal());
            balanceInput.setText(String.valueOf(account.getBalance()));
            noteInput.setText(account.getNote());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        nameInput = null;
        typeSpinner = null;
        balanceInput = null;
        noteInput = null;
        saveButton = null;
        cancelButton = null;
        account = null;
    }
} 