package com.example.accountapp.ui.home;

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
import com.example.accountapp.databinding.FragmentHomeBinding;
import com.example.accountapp.ui.transactions.AddTransactionFragment;
import com.example.accountapp.ui.transactions.TransactionAdapter;
import com.example.accountapp.ui.transactions.TransactionDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.List;
import com.example.accountapp.ui.transactions.TransactionsViewModel;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private TransactionsViewModel viewModel;
    private TransactionAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        observeData();
    }

    private void setupViews() {
        adapter = new TransactionAdapter(transaction -> {
            new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Delete", (dialog, which) -> viewModel.delete(transaction))
                .setNegativeButton("Cancel", null)
                .show();
        });

        binding.recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewTransactions.setAdapter(adapter);

        binding.fabAddTransaction.setOnClickListener(v -> {
            AddTransactionFragment fragment = new AddTransactionFragment();
            fragment.show(getChildFragmentManager(), "AddTransaction");
        });
    }

    private void observeData() {
        viewModel.getTransactions().observe(getViewLifecycleOwner(), transactions -> {
            adapter.submitList(transactions);
            binding.textViewNoTransactions.setVisibility(transactions.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 