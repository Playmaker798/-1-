package com.example.accountapp.ui.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Category;
import com.google.android.material.card.MaterialCardView;

public class CategoriesAdapter extends ListAdapter<Category, CategoriesAdapter.CategoryViewHolder> {
    private final OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
        void onCategoryLongClick(Category category);
    }

    public CategoriesAdapter(OnCategoryClickListener listener) {
        super(new DiffUtil.ItemCallback<Category>() {
            @Override
            public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
                return oldItem.getName().equals(newItem.getName()) &&
                       oldItem.getType() == newItem.getType() &&
                       oldItem.getIcon().equals(newItem.getIcon()) &&
                       oldItem.getColor().equals(newItem.getColor());
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final ImageView imageViewIcon;
        private final TextView textViewName;
        private final TextView textViewType;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            imageViewIcon = itemView.findViewById(R.id.imageViewCategoryIcon);
            textViewName = itemView.findViewById(R.id.textViewCategoryName);
            textViewType = itemView.findViewById(R.id.textViewCategoryType);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCategoryClick(getItem(position));
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCategoryLongClick(getItem(position));
                    return true;
                }
                return false;
            });
        }

        void bind(Category category) {
            // TODO: Implement icon loading from string
            // For now, use a default icon
            imageViewIcon.setImageResource(R.drawable.ic_category);
            textViewName.setText(category.getName());
            textViewType.setText(category.getType().toString());
            cardView.setCardBackgroundColor(android.graphics.Color.parseColor(category.getColor()));
        }
    }
} 