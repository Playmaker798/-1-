package com.example.accountapp.ui.transactions;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.databinding.ItemTransactionBinding;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TransactionsAdapter extends ListAdapter<TransactionEntity, TransactionsAdapter.TransactionViewHolder> {
    private final OnTransactionClickListener listener;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public interface OnTransactionClickListener {
        void onTransactionClick(TransactionEntity transaction);
        void onTransactionLongClick(TransactionEntity transaction);
    }

    public TransactionsAdapter(OnTransactionClickListener listener) {
        super(new DiffUtil.ItemCallback<TransactionEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                return oldItem.getAmount() == newItem.getAmount() &&
                       oldItem.getDate().equals(newItem.getDate()) &&
                       oldItem.getDescription().equals(newItem.getDescription()) &&
                       oldItem.getType() == newItem.getType();
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionBinding binding = ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void updateTransactions(java.util.List<TransactionEntity> transactions) {
        submitList(transactions);
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final ItemTransactionBinding binding;

        TransactionViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TransactionEntity transaction) {
            binding.textViewAmount.setText(currencyFormat.format(transaction.getAmount()));
            binding.textViewDate.setText(dateFormat.format(transaction.getDate()));
            binding.textViewDescription.setText(transaction.getDescription());
            binding.textViewType.setText(transaction.getType().toString());

            int colorResId = transaction.getType() == TransactionEntity.Type.INCOME ?
                    R.color.income_green : R.color.expense_red;
            binding.textViewAmount.setTextColor(
                    binding.getRoot().getContext().getResources().getColor(colorResId, null));

            binding.getRoot().setOnClickListener(v -> listener.onTransactionClick(transaction));
            binding.getRoot().setOnLongClickListener(v -> {
                listener.onTransactionLongClick(transaction);
                return true;
            });
        }
    }
} 