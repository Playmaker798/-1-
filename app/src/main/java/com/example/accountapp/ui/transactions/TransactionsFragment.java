package com.example.accountapp.ui.transactions;

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
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.databinding.FragmentTransactionsBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.List;

public class TransactionsFragment extends Fragment {
    private FragmentTransactionsBinding binding;
    private TransactionsViewModel viewModel;
    private TransactionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
        setupViews();
        observeData();
    }

    private void setupViews() {
        binding.recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TransactionAdapter(transaction -> {
            TransactionDialogFragment fragment = TransactionDialogFragment.newInstance(transaction.getId());
            fragment.show(getParentFragmentManager(), "TransactionDialog");
        });
        binding.recyclerViewTransactions.setAdapter(adapter);

        binding.fabAddTransaction.setOnClickListener(v -> {
            AddTransactionFragment fragment = new AddTransactionFragment();
            fragment.show(getParentFragmentManager(), "AddTransaction");
        });
    }

    private void observeData() {
        viewModel.getTransactions().observe(getViewLifecycleOwner(), transactions -> {
            adapter.submitList(transactions);
            binding.textViewEmpty.setVisibility(transactions.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 