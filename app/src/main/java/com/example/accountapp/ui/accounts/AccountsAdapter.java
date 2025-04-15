package com.example.accountapp.ui.accounts;

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
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class AccountsAdapter extends ListAdapter<Account, AccountsAdapter.AccountViewHolder> {
    private final OnAccountClickListener listener;

    public interface OnAccountClickListener {
        void onAccountClick(Account account);
        void onAccountLongClick(Account account);
    }

    public AccountsAdapter(OnAccountClickListener listener) {
        super(new DiffUtil.ItemCallback<Account>() {
            @Override
            public boolean areItemsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
                return oldItem.getName().equals(newItem.getName()) &&
                       oldItem.getType() == newItem.getType() &&
                       oldItem.getBalance() == newItem.getBalance();
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void updateAccounts(List<Account> accounts) {
        submitList(accounts);
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final TextView textViewName;
        private final TextView textViewType;
        private final TextView textViewBalance;

        AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            textViewName = itemView.findViewById(R.id.textViewAccountName);
            textViewType = itemView.findViewById(R.id.textViewAccountType);
            textViewBalance = itemView.findViewById(R.id.textViewBalance);
        }

        void bind(Account account) {
            textViewName.setText(account.getName());
            textViewType.setText(account.getType().toString());
            textViewBalance.setText(String.format("$%.2f", account.getBalance()));

            cardView.setOnClickListener(v -> listener.onAccountClick(account));
            cardView.setOnLongClickListener(v -> {
                listener.onAccountLongClick(account);
                return true;
            });
        }
    }
} 