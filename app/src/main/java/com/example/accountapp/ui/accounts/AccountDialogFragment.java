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
import com.google.android.material.textfield.TextInputLayout;
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
    private TextInputLayout nameLayout;
    private TextInputLayout balanceLayout;
    private TextInputLayout typeLayout;

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
        nameLayout = view.findViewById(R.id.layout_account_name);
        balanceLayout = view.findViewById(R.id.layout_balance);
        typeLayout = view.findViewById(R.id.layout_account_type);
    }

    private void setupSpinner() {
        // 添加"请选择"作为第一个选项
        String[] types = new String[Account.AccountType.values().length + 1];
        types[0] = "请选择";
        for (int i = 0; i < Account.AccountType.values().length; i++) {
            types[i + 1] = Account.AccountType.values()[i].getDisplayName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            types
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private void setupButtons() {
        saveButton.setOnClickListener(v -> saveAccount());
        cancelButton.setOnClickListener(v -> dismiss());
    }

    private void saveAccount() {
        String name = nameInput.getText() != null ? nameInput.getText().toString().trim() : "";
        int typeIndex = typeSpinner.getSelectedItemPosition();
        String balanceStr = balanceInput.getText() != null ? balanceInput.getText().toString().trim() : "";
        String note = noteInput.getText() != null ? noteInput.getText().toString().trim() : "";

        boolean hasError = false;
        // 校验账户名
        if (name.isEmpty()) {
            nameLayout.setError("Please enter account name");
            hasError = true;
        } else {
            nameLayout.setError(null);
        }
        // 校验余额
        if (balanceStr.isEmpty()) {
            balanceLayout.setError("Please enter a valid balance");
            hasError = true;
        } else {
            try {
                double balance = Double.parseDouble(balanceStr);
                if (balance < 0) {
                    balanceLayout.setError("Balance cannot be negative");
                    hasError = true;
                } else {
                    balanceLayout.setError(null);
                }
            } catch (NumberFormatException e) {
                balanceLayout.setError("Please enter a valid number");
                hasError = true;
            }
        }
        // 校验类型
        if (typeIndex == 0) { // 选择了"请选择"
            typeLayout.setError("Please select account type");
            hasError = true;
        } else {
            typeLayout.setError(null);
        }
        // 只要有错误就返回
        if (hasError) return;

        // 获取实际选择的类型（需要减1因为第一个是"请选择"）
        Account.AccountType type = Account.AccountType.values()[typeIndex - 1];
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
            // 设置类型时需要加1，因为第一个是"请选择"
            typeSpinner.setSelection(account.getType().ordinal() + 1);
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