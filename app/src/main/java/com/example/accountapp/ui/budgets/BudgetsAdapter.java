package com.example.accountapp.ui.budgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Budget;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class BudgetsAdapter extends ListAdapter<Budget, BudgetsAdapter.BudgetViewHolder> {
    private final OnBudgetClickListener listener;

    public interface OnBudgetClickListener {
        void onBudgetClick(Budget budget);
        void onBudgetLongClick(Budget budget);
    }

    public BudgetsAdapter(List<Budget> budgets, OnBudgetClickListener listener) {
        super(new DiffUtil.ItemCallback<Budget>() {
            @Override
            public boolean areItemsTheSame(@NonNull Budget oldItem, @NonNull Budget newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Budget oldItem, @NonNull Budget newItem) {
                return oldItem.getAmount() == newItem.getAmount() &&
                       oldItem.getSpent() == newItem.getSpent() &&
                       oldItem.getMonth() == newItem.getMonth() &&
                       oldItem.getYear() == newItem.getYear();
            }
        });
        this.listener = listener;
        submitList(budgets);
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void updateBudgets(List<Budget> budgets) {
        submitList(budgets);
    }

    class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final TextView categoryTextView;
        private final TextView amountTextView;
        private final TextView spentTextView;
        private final ProgressBar progressBar;

        BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            categoryTextView = itemView.findViewById(R.id.textViewCategory);
            amountTextView = itemView.findViewById(R.id.textViewAmount);
            spentTextView = itemView.findViewById(R.id.textViewSpent);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        void bind(Budget budget) {
            categoryTextView.setText(budget.getCategoryName());
            amountTextView.setText(String.format("$%.2f", budget.getAmount()));
            spentTextView.setText(String.format("$%.2f", budget.getSpent()));
            
            int progress = (int) ((budget.getSpent() / budget.getAmount()) * 100);
            progressBar.setProgress(progress);

            cardView.setOnClickListener(v -> listener.onBudgetClick(budget));
            cardView.setOnLongClickListener(v -> {
                listener.onBudgetLongClick(budget);
                return true;
            });
        }
    }
} 