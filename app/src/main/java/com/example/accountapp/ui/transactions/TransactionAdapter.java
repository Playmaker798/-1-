package com.example.accountapp.ui.transactions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.entity.TransactionEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends ListAdapter<TransactionEntity, TransactionAdapter.TransactionViewHolder> {
    private final OnTransactionClickListener listener;
    private List<Account> accountList;

    public TransactionAdapter(OnTransactionClickListener listener) {
        super(new DiffUtil.ItemCallback<TransactionEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }

    public void setAccountList(List<Account> accounts) {
        this.accountList = accounts;
        notifyDataSetChanged();
    }

    private String getAccountNameById(long accountId) {
        if (accountList == null) return "";
        for (Account acc : accountList) {
            if (acc.getId() == accountId) return acc.getName();
        }
        return "";
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionEntity transaction = getItem(position);
        String accountName = getAccountNameById(transaction.getAccountId());
        holder.bind(transaction, listener, accountName);
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView amountText;
        private final TextView descriptionText;
        private final TextView dateText;
        private final TextView accountText;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            amountText = itemView.findViewById(R.id.textViewAmount);
            descriptionText = itemView.findViewById(R.id.textViewDescription);
            dateText = itemView.findViewById(R.id.textViewDate);
            accountText = itemView.findViewById(R.id.textViewAccount);
        }

        public void bind(TransactionEntity transaction, OnTransactionClickListener listener, String accountName) {
            amountText.setText(String.format(Locale.getDefault(), "%.2f", transaction.getAmount()));
            descriptionText.setText(transaction.getDescription());
            dateText.setText(formatDate(transaction.getDate()));
            accountText.setText(accountName);
            itemView.setOnClickListener(v -> listener.onTransactionClick(transaction));
        }

        private String formatDate(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            return sdf.format(date);
        }
    }

    public interface OnTransactionClickListener {
        void onTransactionClick(TransactionEntity transaction);
    }
} 