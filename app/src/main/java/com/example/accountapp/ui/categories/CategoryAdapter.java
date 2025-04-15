package com.example.accountapp.ui.categories;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.databinding.ItemCategoryBinding;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    private final OnCategoryClickListener listener;

    public CategoryAdapter(OnCategoryClickListener listener) {
        super(new CategoryDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding binding;

        CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Category category) {
            binding.textViewCategoryName.setText(category.getName());
            binding.textViewCategoryType.setText(category.getType().name());
            // TODO: Set icon and color

            binding.getRoot().setOnClickListener(v -> listener.onCategoryClick(category));
            binding.getRoot().setOnLongClickListener(v -> {
                listener.onCategoryLongClick(category);
                return true;
            });
        }
    }

    private static class CategoryDiffCallback extends DiffUtil.ItemCallback<Category> {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                   oldItem.getType() == newItem.getType() &&
                   oldItem.getIcon().equals(newItem.getIcon()) &&
                   oldItem.getColor() == newItem.getColor();
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
        void onCategoryLongClick(Category category);
    }
} 