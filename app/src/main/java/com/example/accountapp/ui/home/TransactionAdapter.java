package com.example.accountapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.TransactionEntity;

public class TransactionAdapter extends ListAdapter<TransactionEntity, TransactionAdapter.TransactionViewHolder> {
    private final OnTransactionClickListener listener;

    public TransactionAdapter(OnTransactionClickListener listener) {
        super(new DiffUtil.ItemCallback<TransactionEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                return oldItem.getAmount() == newItem.getAmount() &&
                       oldItem.getDescription().equals(newItem.getDescription()) &&
                       oldItem.getDate().equals(newItem.getDate());
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionEntity transaction = getItem(position);
        holder.bind(transaction);
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView amountText;
        private final TextView descriptionText;
        private final TextView dateText;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            amountText = itemView.findViewById(R.id.textViewAmount);
            descriptionText = itemView.findViewById(R.id.textViewDescription);
            dateText = itemView.findViewById(R.id.textViewDate);
        }

        void bind(TransactionEntity transaction) {
            amountText.setText(String.format("%.2f", transaction.getAmount()));
            descriptionText.setText(transaction.getDescription());
            dateText.setText(android.text.format.DateFormat.format("yyyy-MM-dd", transaction.getDate()));
            itemView.setOnClickListener(v -> listener.onTransactionClick(transaction));
        }
    }

    public interface OnTransactionClickListener {
        void onTransactionClick(TransactionEntity transaction);
    }
} 