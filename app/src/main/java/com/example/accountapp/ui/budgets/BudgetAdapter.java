package com.example.accountapp.ui.budgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Budget;
import java.text.NumberFormat;
import java.util.Locale;

public class BudgetAdapter extends ListAdapter<Budget, BudgetAdapter.BudgetViewHolder> {
    private final OnBudgetClickListener listener;

    public BudgetAdapter(OnBudgetClickListener listener) {
        super(new DiffUtil.ItemCallback<Budget>() {
            @Override
            public boolean areItemsTheSame(@NonNull Budget oldItem, @NonNull Budget newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Budget oldItem, @NonNull Budget newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = getItem(position);
        holder.bind(budget, listener);
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryText;
        private final TextView amountText;
        private final TextView spentText;
        private final TextView remainingText;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.textViewCategory);
            amountText = itemView.findViewById(R.id.textViewAmount);
            spentText = itemView.findViewById(R.id.textViewSpent);
            remainingText = itemView.findViewById(R.id.textViewRemaining);
        }

        public void bind(Budget budget, OnBudgetClickListener listener) {
            categoryText.setText(budget.getCategoryName());
            amountText.setText(formatAmount(budget.getAmount()));
            spentText.setText(formatAmount(budget.getSpent()));
            remainingText.setText(formatAmount(budget.getAmount() - budget.getSpent()));
            itemView.setOnClickListener(v -> listener.onBudgetClick(budget));
        }

        private String formatAmount(double amount) {
            return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount);
        }
    }

    public interface OnBudgetClickListener {
        void onBudgetClick(Budget budget);
    }
} 