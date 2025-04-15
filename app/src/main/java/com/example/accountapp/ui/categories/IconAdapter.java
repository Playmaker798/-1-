package com.example.accountapp.ui.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import java.util.ArrayList;
import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {
    private final List<String> icons;
    private String selectedIcon;
    private final OnIconClickListener listener;

    public IconAdapter(OnIconClickListener listener) {
        this.listener = listener;
        this.icons = new ArrayList<>();
        // Add your icon resources here
        icons.add("ic_home");
        icons.add("ic_car");
        icons.add("ic_food");
        icons.add("ic_shopping");
        icons.add("ic_entertainment");
        icons.add("ic_health");
        icons.add("ic_education");
        icons.add("ic_transport");
        icons.add("ic_travel");
        icons.add("ic_gift");
        icons.add("ic_salary");
        icons.add("ic_investment");
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_icon, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        String icon = icons.get(position);
        int iconResId = holder.itemView.getContext().getResources()
                .getIdentifier(icon, "drawable", holder.itemView.getContext().getPackageName());
        holder.imageView.setImageResource(iconResId);
        holder.imageView.setSelected(icon.equals(selectedIcon));
        holder.itemView.setOnClickListener(v -> {
            selectedIcon = icon;
            notifyDataSetChanged();
            listener.onIconClick(icon);
        });
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public void setSelectedIcon(String icon) {
        this.selectedIcon = icon;
        notifyDataSetChanged();
    }

    static class IconViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        IconViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewIcon);
        }
    }

    public interface OnIconClickListener {
        void onIconClick(String icon);
    }
} 