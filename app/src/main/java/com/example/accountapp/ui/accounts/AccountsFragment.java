package com.example.accountapp.ui.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.databinding.FragmentAccountsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AccountsFragment extends Fragment implements AccountsAdapter.OnAccountClickListener {
    private FragmentAccountsBinding binding;
    private AccountsViewModel viewModel;
    private AccountsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupFab();
        observeAccounts();
    }

    private void setupRecyclerView() {
        adapter = new AccountsAdapter(this);
        binding.recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewAccounts.setAdapter(adapter);
    }

    private void setupFab() {
        FloatingActionButton fab = binding.fabAddAccount;
        fab.setOnClickListener(v -> showAccountDialog(null));
    }

    private void observeAccounts() {
        viewModel.getAllAccounts().observe(getViewLifecycleOwner(), accounts -> {
            adapter.submitList(accounts);
            binding.textViewNoAccounts.setVisibility(accounts.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void showAccountDialog(Account account) {
        AccountDialogFragment dialog;
        if (account != null) {
            dialog = AccountDialogFragment.newInstance(account.getId());
        } else {
            dialog = AccountDialogFragment.newInstance();
        }
        dialog.show(getChildFragmentManager(), "AccountDialog");
    }

    @Override
    public void onAccountClick(Account account) {
        showAccountDialog(account);
    }

    @Override
    public void onAccountLongClick(Account account) {
        showDeleteAccountDialog(account);
    }

    private void showDeleteAccountDialog(Account account) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_account)
                .setMessage(R.string.delete_account_confirmation)
                .setPositiveButton(R.string.delete, (dialog, which) -> viewModel.deleteAccount(account))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 